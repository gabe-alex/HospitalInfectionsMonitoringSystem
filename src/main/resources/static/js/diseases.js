var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

spring.requestConfig.baseURL = '/api/';

var Disease = spring.extend('diseases');

himsApp.controller('DbController', function DbController($scope, uiGridConstants) {
    $scope.spreadRiskList = [{id: 'LOW', value: 'LOW'}, {id: 'MEDIUM', value: 'MEDIUM'}, {id: 'HIGH', value: 'HIGH'}];
    $scope.newDisease = {name:'', spreadRisk:''};

    var _initialData;

    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };
    $scope.gridOptions.columnDefs = [
        { name: 'data().name', displayName: 'Name', enableHiding: false},
        { name: 'data().spreadRisk', displayName: 'Spread Risk', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: $scope.spreadRiskList},
        { name:' ', width: 80, width: 100, enableHiding: false, enableSorting: false, enableColumnMenu: false, cellTemplate:'<div><button ng-click="grid.appScope.removeRow(row.entity)">Remove</button></div>'}
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
        rowEntity.remove().then(function() {
            $scope.$apply(function () {
                $scope.gridOptions.data.splice(index, 1);
            });
        });
    };

    $scope.addRow = function() {
        var disease = new Disease($scope.newDisease);
        disease.save().then(function() {
            $scope.$apply(function () {
                $scope.gridOptions.data.push(disease);
                $scope.newDisease = {name:'', spreadRisk:''};
            });
        });
    };

    Disease.findAll().then(function (diseases) {
        $scope.$apply(function () {
            _initialData = angular.copy(diseases);
            $scope.gridOptions.data = diseases;
        });
    }).catch(function(req) {
        done(req);
    });
});