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

var User = spring.entity.extend('users');
var Person = spring.entity.extend('persons');

himsApp.controller('DbController', function DbController($scope, uiGridConstants) {
    $scope.people=[];

    $scope.newUser = {person:'', username:'',password:''};

    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };

    $scope.gridOptions.columnDefs = [
        { name: 'data().person', displayName: 'Person', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: $scope.people},
        { name: 'data().username', displayName: 'Username', enableHiding: false},
        { name: 'data().password', displayName: 'Password', enableHiding: false},

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
        var user = new User($scope.newUser);
        user.save().then(function() {
            $scope.$apply(function () {
                $scope.gridOptions.data.push(user);
                $scope.newUser = {name:'', username:'',password:''};
            });
        });
    };

    Person.findAll().then(function (people) {
        $scope.$apply(function () {
            $scope.people = people;
        });
    }).catch(function(req) {
        done(req);
    });

    User.findAll().then(function (users) {
        $scope.gridOptions.data = [];
            for(var i=0; i<users.length; i++) {
                var user = users[i];
                console.log(user);
                user.fetchProperty('person', Person).then(function(person) {
                    $scope.$apply(function() {
                        $scope.gridOptions.data.push(user);
                    });
                }).catch(function(req) {
                    console.error(req);
                });
            }
    }).catch(function(req) {
        console.error(req);
    });
});