'use strict';

angular.module('diabblogApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


