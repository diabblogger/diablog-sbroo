'use strict';

angular.module('diabblogApp')
    .controller('EntryController', function ($scope, Entry, ParseLinks) {
        $scope.entrys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Entry.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.entrys.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.entrys = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Entry.update($scope.entry,
                function () {
                    $scope.reset();
                    $('#saveEntryModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Entry.get({id: id}, function(result) {
                $scope.entry = result;
                $('#saveEntryModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Entry.get({id: id}, function(result) {
                $scope.entry = result;
                $('#deleteEntryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Entry.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteEntryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.entry = {bloodSugarLevel: null, carbs: null, comments: null, notes: null, correction: null, created: null, fastInsulin: null, slowInsulin: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
