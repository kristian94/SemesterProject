var bonierApp = angular.module('bonierApp', ['ngRoute',
  'ngAnimate',
  'angular-jwt',
  'ui.bootstrap', 'bonierControllers', 'bonierSecurity', 'bonierFactories']);
bonierApp.config(function($routeProvider, $httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
    $routeProvider.
    when('/flights', {
        templateUrl: 'partials/result-list.html',
        controller: 'resultListCtrl'
    }).
    when('/start', {
        templateUrl: 'partials/start.html',
        controller: 'searchCtrl'
    }).
    when('/register', {
        templateUrl: 'partials/registration.html',
        controller: ''
    }).
    otherwise({
        redirectTo: '/start'
    });
});
bonierApp.directive('boniernavbar', function() {
    return {
        restrict : 'E',
        templateUrl: 'partials/navbar.html'
    };
});
