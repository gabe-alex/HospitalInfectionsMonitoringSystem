var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

spring.requestConfig.baseURL = '/api/';

var Beacon = spring.extend('beacons');

himsApp.controller('DbController', function DbController($scope, uiGridConstants) {
    $scope.locationTypeList = [{id: 'SINK', value: 'SINK'}, {id: 'DOOR', value: 'DOOR'}, {id: 'BED', value: 'BED'}];

    $scope.newBeacon = {mac_address:'', location_type:'', location_description:''};

    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };
    $scope.gridOptions.columnDefs = [
        { name: 'data().mac_address', displayName: 'Mac address', enableHiding: false},
        { name: 'data().location_type', displayName: 'Location Type', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: $scope.locationTypeList},
        { name: 'data().location_description', displayName: 'Location Description', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', enableHiding: false},

        { name:' ', width: 100, enableHiding: false, enableSorting: false, enableColumnMenu: false, cellTemplate:'<div><button ng-click="grid.appScope.removeRow(row.entity)">Remove</button></div>'}
    ];

    $scope.gridOptions.onRegisterApi = function(gridApi){
        //set gridApi on scope
        $scope.gridApi = gridApi;
        gridApi.rowEdit.on.saveRow($scope, $scope.saveRow);
    };

    $scope.saveRow = function( rowEntity ) {
        var promise = rowEntity.save();
        $scope.gridApi.rowEdit.setSavePromise(rowEntity, promise);
    };

    $scope.removeRow = function(rowEntity) {
        var index = $scope.gridOptions.data.indexOf(rowEntity);
        rowEntity.delete().then(function() {
            $scope.$apply(function () {
                $scope.gridOptions.data.splice(index, 1);
            });
        });
    };

    $scope.addRow = function() {
        var beacon = new Beacon($scope.newBeacon);
        beacon.save().then(function() {
            $scope.$apply(function () {
                $scope.gridOptions.data.push(beacon);
                $scope.newBeacon = {mac_address:'', location_type:'', location_description:''};
            });
        });
    };

    Beacon.findAll().then(function (beacons) {
        $scope.$apply(function () {
            $scope.gridOptions.data = beacons;
        });
    }).catch(function(req) {
        done(req);
    });
});