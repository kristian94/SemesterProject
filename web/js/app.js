var bonierApp = angular.module('bonierApp', ['ngRoute', 'bonierControllers'])
bonierApp.config(function($routeProvider) {
        $routeProvider.
		when('/flights', {
            templateUrl: 'partials/result-list.html',
            controller: 'resultListCtrl'
        }).
		when('/start', {
            templateUrl: 'partials/start.html',
            controller: 'searchCtrl'
        }).
        otherwise({
            redirectTo: '/start',
        });
    });
