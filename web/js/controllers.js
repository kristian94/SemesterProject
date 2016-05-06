var app = angular.module('bonierControllers', []);

app.controller('resultListCtrl', function($scope, searchResultFactory) {
    $scope.result = searchResultFactory.getSearchResult();
    $scope.search = searchResultFactory.getSearchParameters();
    console.log($scope.result);
    console.log($scope.search);

    $scope.searchUpdate = function() {
        if ($scope.search.origin == undefined || $scope.search.date == undefined || $scope.search.numberOfSeats == undefined) {
            console.log("a parameter undefined");
            return;
        }
        var date = new Date($scope.search.date)
        var dateUTC = date.getTime() - (date.getTimezoneOffset() * 60000);
        var isoDate = new Date(dateUTC).toISOString();

        var searchParameters = {
            "origin": $scope.search.origin,
            "destination": $scope.search.destination,
            "date": isoDate,
            "numberOfSeats": $scope.search.numberOfSeats
        };

        //start loading animation here
        searchResultFactory.search(searchParameters).then(function(result) {
            $scope.result = result;
            console.log("new scope result " + $scope.result);
            //stop loading animation here
        });
    };
});

app.controller('searchCtrl', function($scope, searchResultFactory) {
    $scope.search = function() {
        var date = new Date($scope.date)
        var dateUTC = date.getTime() - (date.getTimezoneOffset() * 60000);
        var isoDate = new Date(dateUTC).toISOString();

        var searchParameters = {
            "origin": $scope.from,
            "destination": $scope.to,
            "date": isoDate,
            "numberOfSeats": $scope.seats
        };
        //todo check input before post request
        searchResultFactory.search(searchParameters);
        searchParameters.date = $scope.date
        searchResultFactory.saveSearchParameters(searchParameters)
    };
});

var errorGlyph = '<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> '
var successGlyph = '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> '

app.controller('formController', ['$scope', '$http', function($scope, $http, $window) {
    $scope.create = function(user) {
        $('#registration .alert').remove();
        //validation
        if (user.userName == undefined || user.firstName == undefined || user.lastName == undefined || user.email == undefined || user.password.trim() === "" || user.passwordRep.trim() === "") {
            $('#registration .alert').remove();
            $('#registration').prepend('<div class="alert alert-danger id="message-box">' + errorGlyph + 'Please fill all the fields with a valid input</div>');
            return;
        } else if (user.password.length < 6) {
            $('#registration .alert').remove();
            $('#registration')
                .prepend('<div class="alert alert-danger id="message-box">' + errorGlyph + 'Password length needs to be at least 6 characters long</div>');
            return;
        } else if (user.password !== user.passwordRep) {
            $('#registration .alert').remove();
            $('#registration').prepend('<div class="alert alert-danger id="message-box">' + errorGlyph + 'Passwords do not match</div>');
            return;
        }

        //creation post call
        //delete user.passwordRep;
        $http.post('/SemesterProject/api/user', user)
            .success(function(data) {
            var dataBool = data === "ok";
            $('#registration .alert').remove();
            $('#registration').prepend('<div class="alert ' + (dataBool ? 'alert-success' : 'alert-danger') + '" id="message-box">' + (dataBool ? successGlyph : errorGlyph) + (dataBool ? 'Account created' : 'Username taken') + '</div>');
            if (dataBool) {
                $('#createUserForm').remove();
            }
        });
    };
}]);

app.factory('searchResultFactory', function($http, $location, $q) {
    var searchResult = [];
    var searchParameters = [];
    var baseUrl = "api/flight";

    return {
        search: function(searchParameters) {
            console.log("in search factory");
            var deferred = $q.defer();
            $http.post(baseUrl, searchParameters)
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
            console.log("getting search result");
            return searchResult;
        },
        saveSearchParameters: function(data) {
            searchParameters = data;
            console.log("saving parameters " + data);
        },
        getSearchParameters: function(data) {
            console.log("getting search parameters");
            return searchParameters;
        }
    };
});

app.controller('profileCtrl', function($http, $window, jwtHelper, $scope) {
    var init = function() {
        var token = $window.sessionStorage.id_token;
        if (token) {
            // function in auth.js
            initializeFromToken($scope, $window.sessionStorage.id_token, jwtHelper);
        }
    };
    init();

    $scope.user = {};
    $http.get('api/user/' + $scope.username)
        .success(function(data) {
            console.log(data);
            $scope.user = data;
        })
        .error(function(data) {
            console.log(data);
            console.log("handle this error");
        });

    $http.get('api/booking')
        .success(function(data) {
            console.log(data);
        })
        .error(function(data) {
            console.log(data);
        });
});

app.controller('adminCtrl', function($http, $window, jwtHelper, $scope) {
    var init = function() {
        var token = $window.sessionStorage.id_token;
        if (token) {
            // function in auth.js
            initializeFromToken($scope, $window.sessionStorage.id_token, jwtHelper);
        }
    };
    init();

    $scope.users = [];
    $http.get('api/user/all')
        .success(function(data) {
            console.log(data);
            $scope.users = data;
        })
        .error(function(data) {
            console.log(data);
        });
});

app.controller('adminDetailsCtrl', function($scope, $routeParams, $http) {
    $scope.username = $routeParams.username;
    //todo get bookings the user in routeParams
    $http.get('api/booking')
        .success(function(data) {
            console.log(data);
        })
        .error(function(data) {
            console.log(data);
        });
});
