var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

spring.requestConfig.baseURL = '/api/';

var User = spring.extend('users');
var Person = spring.extend('persons');

himsApp.controller('DbController', function DbController($scope, uiGridConstants) {
    $scope.people=[];

    $scope.newUser = {person:'', username:'',password:''};

    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };

    $scope.gridOptions.columnDefs = [
        { name: 'data().username', displayName: 'Username', enableHiding: false},
        { name: 'data().person', displayName: 'Person', enableHiding: false, cellFilter: 'personCellDisplay', editableCellTemplate: 'ui-grid/dropdownEditor'},

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
            var parsedPeople = [];
            for(var i=0; i<people.length; i++) {
                var person = people[i];
                parsedPeople.push({id: person, value: person.data().name});
            }
            $scope.people = parsedPeople;
            $scope.gridOptions.columnDefs[1].editDropdownOptionsArray = parsedPeople;
        });
    }).catch(function(req) {
        console.error(req);
    });

    User.findAll().then(function (users) {
        var promises = [];
        for(var i=0; i<users.length; i++) {
            var user = users[i];
            promises.push(user.fetchProperty('person', Person));
            Promise.all(promises).then(function() {
                $scope.$apply(function () {
                    $scope.gridOptions.data = users;
                });
            });
        }
    }).catch(function(req) {
        console.error(req);
    });
})
.filter('personDisplay', function() {
    return function(input) {
        if (!input){
            return '';
        } else {
            return input.data().name;
        }
    };
})