var app = angular.module('bonierControllers', [])
var baseUrl = "http://localhost:8080/SemesterProject/api/flight"
app.controller('resultListCtrl', function($scope, $http, searchResultFactory) {
    $scope.result = searchResultFactory.getSearchResult();
    $scope.parameters = searchResultFactory.getSearchParameters();


    // todo make this a service
    $scope.searchUpdate = function() {
        var isoDate = new Date($scope.date);
        // var isoDate = new Date($scope.date).toISOString();
        var searchParameters = {
            "origin": $scope.search.origin,
            "destination": $scope.search.destination,
            "date": isoDate,
            "numberOfSeats": $scope.search.numberOfSeats
        };
        //todo check input before post request

        $http.post(baseUrl, searchParameters)
            .success(function(data, status) {
                $scope.result = data;
                console.log($scope.result);
            })
            .error(function(data, status) {
                console.log("unhandled error");
                console.log($scope.result);
            });
    };
});

app.controller('searchCtrl', function($scope, $http, $location, searchResultFactory) {



    $(document).ready(function() {
        $('#datepicker').datepicker({
            minDate: 0,
            dayNamesMin: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        });
    });



    $scope.search = function() {
        //        var isoDate = new Date($scope.date);
        var isoDate = $('#datepicker').datepicker("getDate");
        //        console.log($scope.date);
        console.log(isoDate);
        // var isoDate = new Date($scope.date).toISOString();
        var searchParameters = {
            "origin": $scope.from,
            "destination": $scope.to,
            "date": isoDate,
            "numberOfSeats": $scope.seats
        };
        //todo check input before post request

        // todo make this a service
        $http.post(baseUrl, searchParameters)
            .success(function(data, status) {
                searchResultFactory.saveSearchResult(data);
                searchParameters.date = $scope.date
                console.log(searchParameters.date);
                searchResultFactory.saveSearchParameters(searchParameters)
                $location.url('/flights');
            })
            .error(function(data, status) {
                console.log("unhandled error");
            });
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

app.factory('searchResultFactory', function() {
    var searchResult = [];
    var searchParameters = [];

    return {
        saveSearchResult: function(data) {
            searchResult = data;
            console.log("saving result " + data);
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

    /* get bookings
    $http.get('api/booking')
        .success(function(data) {
            console.log(data);
        })
        .error(function(data) {
            console.log(data);
        });
    */
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
