'use strict';

/* Place your global Factory-service in this file */

angular.module('bonierFactories', []).
        factory('InfoFactory', function () {
            var info = "Hello World from a Factory";
            var getInfo = function getInfo() {
                return info;
            };
            return {
                getInfo: getInfo
            };
        }).factory('AuthFactory', function () {

    var $scope;
    var isAuthenticated;
    var username;
    var isAdmin;
    var isUser;

    function getScope() {
        return $scope;
    }
    ;

    function setScope(input) {
        $scope = input;
    }
    ;

    function getIsAuthenticated() {
        return isAuthenticated;
    }

    function getUsername() {
        return username;
    }

    function getIsAdmin() {
        return isAdmin;
    }

    function getIsUser() {
        return isUser;
    }

    function setIsAuthenticated(input) {
        isAuthenticated = input;
    }

    function setUsername(input) {
        username = input;
    }

    function setIsAdmin(input) {
        isAdmin = input;
    }

    function setIsUser(input) {
        isUser = input;
    }

    return {
        getScope: getScope,
        getIsAuthenticated: getIsAuthenticated,
        getUsername: getUsername,
        getIsAdmin: getIsAdmin,
        getIsUser: getIsUser,
        setScope: setScope,
        setIsAuthenticated: setIsAuthenticated,
        setUsername: setUsername,
        setIsAdmin: setIsAdmin,
        setIsUser: setIsUser
    };

});
