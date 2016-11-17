var himsApp = angular.module('himsApp', ['ui.grid', 'ui.grid.autoResize', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.resizeColumns', 'ui.select']);

spring.requestConfig.baseURL = '/api/';

var Disease = spring.extend('diseases');
var Person = spring.extend('persons');

himsApp.controller('DbController', function DbController($scope, uiGridConstants) {
    $scope.roleList = [{id: 'DOCTOR', value: 'DOCTOR'}, {id: 'PATIENT', value: 'PATIENT'}];
    $scope.newPerson = {name:'', age:'', role:'', infectious_disease:'', phone_mac_address:''};
    $scope.diseases=[];

    var _initialData;

    $scope.gridOptions = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        appScopeProvider: $scope
    };
    $scope.gridOptions.columnDefs = [
        { name: 'data().name', displayName: 'Name', enableHiding: false},
        { name: 'data().age', displayName: 'Age', enableHiding: false},
        { name: 'data().role', displayName: 'Role', enableHiding: false, editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: $scope.roleList},
        { name: 'data().diseases', displayName: 'Infectious Diseases', enableHiding: false, cellFilter: 'diseasesDisplay',editableCellTemplate: 'uiSelect'},
        { name: 'data().phone_mac_address', displayName: 'Phone MAC Address', enableHiding: false},

        { name:' ', width: 100, editable: false, enableHiding: false, enableSorting: false, enableColumnMenu: false, cellTemplate:'<div><button ng-click="grid.appScope.removeRow(row.entity)">Remove</button></div>'}
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
        var person= new Person($scope.newPerson);
        person.save().then(function() {
            $scope.$apply(function () {
                $scope.gridOptions.data.push(person);
                $scope.newPerson = {name:'', age:'', diseases:'', phone_mac_address:''};
            });
        });
    };

    Disease.findAll().then(function (diseases) {
        $scope.$apply(function () {
            $scope.diseases = diseases;
            $scope.gridOptions.columnDefs[3].editDropdownOptionsArray = diseases;
        });
    }).catch(function(req) {
        console.error(req);
    });

    Person.findAll().then(function (people) {
        var promises = [];
        for(var i=0; i<people.length; i++) {
            var person = people[i];
            promises.push(person.fetchArrayProperty('diseases', Disease));
            Promise.all(promises).then(function() {
                $scope.$apply(function () {
                    _initialData = angular.copy(people);
                    $scope.gridOptions.data = people;
                });
            });
        }
    }).catch(function(req) {
        console.error(req);
    });

})
.filter('diseasesDisplay', function() {
    return function(input) {
        if (!input){
            return '';
        } else {
            var diseasesNames = [];
            for(var i=0; i<input.length; i++) {
                var disease = input[i];
                diseasesNames.push(disease.data().name);
            }
            return diseasesNames.join(', ') ;
        }
        return input;
    };
})
.directive('uiSelectWrap', function uiSelectWrap($document, uiGridEditConstants) {
    return function link($scope, $elm, $attr) {
        $document.on('click', docClick);

        function docClick(evt) {
            if ($(evt.target).closest('.ui-select-container').size() === 0) {
                $scope.$emit(uiGridEditConstants.events.END_CELL_EDIT);
                $document.off('click', docClick);
            }
        }
    };
});