var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

spring.requestConfig.baseURL = '/api/';

var Interactions = spring.extend('interactions');
var Person = spring.extend('persons');
var Beacon = spring.extend('beacons');

himsApp.controller('DbController', function DbController($scope, $http, uiGridConstants) {
    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };

    $scope.gridOptions.columnDefs = [
        { name: 'data().selfPerson.data().name', width: 200, displayName: 'User', enableHiding: false},
        { name: 'data().targetPerson.data().name', width: 200, displayName: 'Other User', enableHiding: false},
        { name: 'data().targetBeacon.data().location_description', displayName: 'Other Beacon', enableHiding: false},
        { name: 'data().startTimestamp', width: 200, displayName: 'Meeting Start Time', enableHiding: false, cellFilter: "date : 'dd.MM.yyyy HH:mm:ss'", sort: {direction: uiGridConstants.ASC, priority: 0}},
        { name: 'data().endTimestamp', width: 200, displayName: 'Meeting End Time', enableHiding: false, cellFilter: "date : 'dd.MM.yyyy HH:mm:ss'"}
    ];

    Interactions.search('getValid').then(function (interactions) {
        var promises = [];
        for(var i=0; i<interactions.length; i++) {
            var interaction = interactions[i];
            promises.push(interaction.fetchProperty('selfPerson', Person));
            promises.push(interaction.fetchProperty('targetPerson', Person).catch(function() {}));
            promises.push(interaction.fetchProperty('targetBeacon', Beacon).catch(function() {}));
            Promise.all(promises).then(function() {
                $scope.$apply(function () {
                    $scope.gridOptions.data = interactions;
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
});