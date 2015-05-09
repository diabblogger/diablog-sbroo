'use strict';

angular.module('diabblogApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('entry', {
                parent: 'entity',
                url: '/entry',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'diabblogApp.entry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/entry/entrys.html',
                        controller: 'EntryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('entry');
                        return $translate.refresh();
                    }]
                }
            })
            .state('entryDetail', {
                parent: 'entity',
                url: '/entry/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'diabblogApp.entry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/entry/entry-detail.html',
                        controller: 'EntryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('entry');
                        return $translate.refresh();
                    }]
                }
            });
    });
