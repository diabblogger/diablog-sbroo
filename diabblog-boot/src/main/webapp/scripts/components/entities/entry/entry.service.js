'use strict';

angular.module('diabblogApp')
    .factory('Entry', function ($resource) {
        return $resource('api/entrys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.created = new Date(data.created);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
