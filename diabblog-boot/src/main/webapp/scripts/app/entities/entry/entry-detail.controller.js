'use strict';

angular.module('diabblogApp')
    .controller('EntryDetailController', function ($scope, $stateParams, Entry) {
        $scope.entry = {};
        $scope.load = function (id) {
            Entry.get({id: id}, function(result) {
              $scope.entry = result;
            });
        };
        $scope.load($stateParams.id);
    });
