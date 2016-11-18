var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

var serviceUrl = '/srv/getAlerts';

himsApp.controller('DbController', function DbController($scope, $http, uiGridConstants) {
    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };

    $scope.gridOptions.columnDefs = [
        { name: 'interaction.self.name', displayName: 'User 1', enableHiding: false},
        { name: 'interaction.targetPerson.name', displayName: 'User 2', enableHiding: false},
        { name: 'selfToTargetDiseases', displayName: 'Diseases U1 -> U2', enableHiding: false, cellFilter: 'diseasesDisplay'},
        { name: 'targetToSelfDiseases', displayName: 'Diseases U2 -> U1', enableHiding: false, cellFilter: 'diseasesDisplay'},
        { name: 'interaction.startTimestamp', displayName: 'Meeting Start Time', enableHiding: false, cellFilter: "date : 'HH:mm:ss, dd.MM.yyyy' : '+0300'"},
        { name: 'interaction.endTimestamp', displayName: 'Meeting End Time', enableHiding: false, cellFilter: "date : 'HH:mm:ss, dd.MM.yyyy' : '+0300'"}
    ];

    function fetchData() {
        $http({
            url:serviceUrl,
            type:'GET'})
            .success(function(data) {
                $scope.gridOptions.data = data;
            });
    }
    fetchData();
})
    .filter('diseasesDisplay', function() {
        return function(input) {
            if (!input){
                return '';
            } else {
                var diseasesNames = [];
                for(var i=0; i<input.length; i++) {
                    var disease = input[i];
                    diseasesNames.push(disease.name);
                }
                return diseasesNames.join(', ') ;
            }
            return input;
        };
    });