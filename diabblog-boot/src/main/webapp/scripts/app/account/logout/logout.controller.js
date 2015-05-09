'use strict';

angular.module('diabblogApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
