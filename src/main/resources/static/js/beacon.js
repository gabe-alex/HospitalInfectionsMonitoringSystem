var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

spring.requestConfig.baseURL = '/api/';

var Beacon = spring.extend('beacons');

himsApp.controller('DbController', function DbController($scope, uiGridConstants) {
    $scope.locationTypeList = [{id: 'SINK', value: 'SINK'}, {id: 'DOOR', value: 'DOOR'}, {id: 'BED', value: 'BED'}];
    $scope.newBeacon = {mac_address:'', location_type:'', location_description:''};

    var _initialData;

    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };
    $scope.gridOptions.columnDefs = [
        { name: 'data().mac_address', width: 200, displayName: 'Mac address', enableHiding: false},
        { name: 'data().location_type', width: 180, displayName: 'Location Type', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: $scope.locationTypeList},
        { name: 'data().location_description', displayName: 'Location Description', enableHiding: false},

        { name:' ', width: 80, enableHiding: false, enableSorting: false, enableColumnMenu: false, cellTemplate:'<div><button ng-click="grid.appScope.removeRow(row.entity)">Remove</button></div>'}
    ];

    $scope.gridOptions.onRegisterApi = function(gridApi){
        //set gridApi on scope
        $scope.gridApi = gridApi;
        gridApi.rowEdit.on.saveRow($scope, $scope.saveRow);
    };

    $scope.saveRow = function( rowEntity ) {
        for(var i=0; i<_initialData.length; i++) {
            if(_initialData[i].id == rowEntity.id) {
                var changedEntity = _initialData[i];

                for(var property in rowEntity.data())
                {
                    if(rowEntity.data().hasOwnProperty(property)) {
                        changedEntity.set(property, rowEntity.get(property));
                    }
                }

                var promise = changedEntity.save();
                $scope.gridApi.rowEdit.setSavePromise(rowEntity, promise);
            }
        }
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
            _initialData = angular.copy(beacons);
            $scope.gridOptions.data = beacons;
        });
    }).catch(function(req) {
        done(req);
    });
});