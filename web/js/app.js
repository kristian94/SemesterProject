var bonierApp = angular.module('bonierApp', [
    'ngRoute','ngAnimate','angular-jwt','ui.bootstrap.modal', 'ui.bootstrap.tpls', 'bonierControllers', 'bonierSecurity', 'bonierFactories', 'bonierFilters'
]);

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
        controller: 'formController'
    }).
    when('/profile', {
        templateUrl: 'partials/profile.html',
        controller: 'profileCtrl'
    }).
    when('/admin', {
        templateUrl: 'partials/admin.html',
        controller: 'adminCtrl'
    }).
    when('/admin/:username', {
        templateUrl: 'partials/admin-details.html',
        controller: 'adminDetailsCtrl'
    }).
    otherwise({
        redirectTo: '/start'
    });
});

bonierApp.directive('boniernavbar', function() {
    return {
        restrict: 'E',
        templateUrl: 'partials/navbar.html'
    };
});
