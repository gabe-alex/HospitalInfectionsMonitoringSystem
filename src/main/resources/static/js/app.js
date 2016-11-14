var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

spring.request.config = {
    baseURL: '/api/',

    globalFetchOptions: {
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'same-origin'
    }
};

var Disease = spring.entity.extend('diseases');
/*Disease.profileURL = function() {
    return entityConfig.restBaseURL+'/'+this.entityName;
};
Disease.profile = function() {
    return new Promise(function(resolve, reject) {
        request.get(this.profileURL()+'/'+id).send().then(function(json) {
            resolve(json);
        }).catch(function(err)  {
            reject(err);
        })
    })
};
Disease.spreadRisks = function() {
    return new Promise(function(resolve, reject) {
        this.profile().then(function(profileJson) {
            var descriptors = profileJson.alps.descriptors;
            for(var i=0; i<descriptors.length; i++) {
                var descriptor = descriptors[i];
                if(descriptor.id == 'disease-representation') {
                    var descriptors2 = descriptor.descriptors;
                    for(var i2=0; i2<descriptors2.length; i2++) {
                        var descriptor2 = descriptors2[i2];
                        if (descriptor.name == 'spreadRisk') {
                            return resolve(descriptor.doc.value);
                        }
                    }
                }
            }
            reject("spreadRisks not found");
        }).catch(function(err)  {
            reject(err);
        })
    })
};*/

himsApp.controller('DbController', function DbController($scope, uiGridConstants) {
    $scope.spreadRiskList = [{id: 'LOW', value: 'LOW'}, {id: 'MEDIUM', value: 'MEDIUM'}, {id: 'HIGH', value: 'HIGH'}];

    $scope.newDisease = {name:'', spreadRisk:''};

    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };
    $scope.gridOptions.columnDefs = [
        { name: 'data().name', displayName: 'Name', enableHiding: false},
        { name: 'data().spreadRisk', displayName: 'Spread Risk', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: $scope.spreadRiskList},
        {name:' ', width: 100, enableHiding: false, enableSorting: false, enableColumnMenu: false, cellTemplate:'<div><button ng-click="grid.appScope.removeRow(row.entity)">Remove</button></div>'}
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
            $scope.gridOptions.data = diseases;
        });
    }).catch(function(req) {
        done(req);
    });
});