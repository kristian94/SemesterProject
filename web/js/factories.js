'use strict';

/* Place your global Factory-service in this file */

angular.module('bonierFactories', []).
factory('InfoFactory', function() {
    var info = "Hello World from a Factory";
    var getInfo = function getInfo() {
        return info;
    };
    return {
        getInfo: getInfo
    };
}).factory('AuthFactory', function() {

    var $scope;
    var isUser;
    var isAdmin;

    function getScope() {
        return $scope;
    };

    function setScope(input) {
        $scope = input;
    };

    return {
        getScope: getScope,
        setScope: setScope
    };

    function getIsUser() {
        return isUser;
    };

    function getIsAdmin() {
        return isAdmin;
    };

    function setIsUser(input) {
        isUser = input;
    };

    function setIsAdmin(input) {
        isAdmin = input;
    };

    return {
        getIsUser: getIsUser,
        getIsAdmin: getIsAdmin,
        setIsUser: setIsUser,
        setIsAdmin: setIsAdmin

    };

});
