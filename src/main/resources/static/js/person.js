var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

spring.requestConfig.baseURL = '/api/';

var Disease = spring.extend('diseases');
var Person = spring.extend('persons');

himsApp.controller('DbController', function DbController($scope, uiGridConstants) {
    $scope.roleList = [{id: 'DOCTOR', value: 'DOCTOR'}, {id: 'PATIENT', value: 'PATIENT'}];
    $scope.diseases=[];

    $scope.newPerson = {name:'', age:'', role:'', infectious_disease:'', phone_mac_address:''};

    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };
    $scope.gridOptions.columnDefs = [
        { name: 'data().name', displayName: 'Name', enableHiding: false},
        { name: 'data().age', displayName: 'Age', enableHiding: false},
        { name: 'data().role', displayName: 'Role', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: $scope.roleList},
        { name: 'data().infectious_disease', displayName: 'Infectious Disease', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: $scope.diseases},
        { name: 'data().phone_mac_address', displayName: 'Phone MAC Address', enableHiding: false},

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
        var person= new Person($scope.newPerson);
        person.save().then(function() {
            $scope.$apply(function () {
                $scope.gridOptions.data.push(person);
                $scope.newPerson = {name:'', age:'', infectious_disease:'', phone_mac_address:''};
            });
        });
    };

    Disease.findAll().then(function (diseases) {
        $scope.$apply(function () {
            $scope.diseases = diseases;
        });
    }).catch(function(req) {
        done(req);
    });

    Person.findAll().then(function (persons) {
        $scope.$apply(function () {
            $scope.gridOptions.data = persons;
        });
    }).catch(function(req) {
        done(req);
    });

});