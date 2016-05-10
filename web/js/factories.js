'use strict';

/* Place your global Factory-service in this file */

angular.module('bonierFactories', [])
        .factory('InfoFactory', function () {
            var info = "Hello World from a Factory";
            var getInfo = function getInfo() {
                return info;
            };
            return {
                getInfo: getInfo
            };
        })
        .factory('AuthFactory', function () {

            var user = {};

            function setUser(input){
                user = input;
                console.log("current user: ");
                console.log(user);
            }

            function getUser(){
                return user;
            }

            function resetUser(){
                user = {};
            }

            return {
                setUser: setUser,
                getUser: getUser,
                resetUser: resetUser
            };

        })
        .factory('searchResultFactory', function($http, $location, $q) {
            var searchResult = [];
            var searchParameters = [];
            var baseUrl = "api/flight";

            return {
                search: function(parameters) {
                    var deferred = $q.defer();
                    $http.post(baseUrl, parameters)
                        .success(function(data, status) {
                            console.log(data);
                            searchResult = data;
                            $location.url('/flights');
                            deferred.resolve(data);
                        })
                        .error(function(data, status) {
                            console.log("unhandled error");
                            deferred.reject(data);
                        });
                    return deferred.promise;
                },
                getSearchResult: function() {
                    return searchResult;
                },
                saveSearchParameters: function(data) {
                    searchParameters = data;
                },
                getSearchParameters: function(data) {
                    return searchParameters;
                }
            };
        });
