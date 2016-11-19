var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.cellNav', 'ui.grid.resizeColumns']);

var serviceUrl = '/srv/getRuleBreaks';

himsApp.controller('DbController', function DbController($scope, $http, uiGridConstants) {
    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };

    $scope.gridOptions.columnDefs = [
        { name: 'person.name', width: 100, displayName: 'User', enableHiding: false},
        { name: 'otherPerson.name', width: 100, displayName: 'Other User', enableHiding: false},
        { name: 'beacon.location_description', width: 300, displayName: 'Location', enableHiding: false},
        { name: 'message', displayName: 'Violation', enableHiding: false}
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
});