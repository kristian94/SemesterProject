var app = angular.module('bonierControllers', []);

app.controller('resultListCtrl', function($scope, $window, jwtHelper, searchResultFactory) {
    var init = function() {
        var token = $window.sessionStorage.id_token;
        if (token) {
            initializeFromToken($scope, $window.sessionStorage.id_token, jwtHelper); // function in auth.js
        }
    };
    init();
    $scope.result = searchResultFactory.getSearchResult();
    $scope.search = searchResultFactory.getSearchParameters();

    $scope.searchUpdate = function() {
        if ($scope.search.origin == undefined || $scope.search.date == undefined || $scope.search.numberOfSeats == undefined) {
            console.log("a parameter undefined");
            //todo visually show which parameter is undefined
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

    $scope.bookTickets = function() {
        console.log("booking tickets");
        //todo confirm modal?
        //send post
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
        searchResultFactory.search(searchParameters).then(function(result) {
            searchParameters.date = $scope.date;
            searchResultFactory.saveSearchParameters(searchParameters);
        });
    };
});

var errorGlyph = '<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> '
var successGlyph = '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> '

app.controller('formController', ['$scope', '$http', function($scope, $http, $window) {
    $scope.create = function(user) {
        $('#registration .alert').remove();
        //validation
        var re = /\D/
        if (user.userName == undefined || user.firstName == undefined || user.lastName == undefined || user.email == undefined || user.password.trim() === "" || user.passwordRep.trim() === "") {
            $('#registration .alert').remove();
            $('#registration').prepend('<div class="alert alert-danger id="message-box">' + errorGlyph + 'Please fill all the fields with a valid input</div>');
            return;
        } else if (re.test(user.phone)) {
            $('#registration .alert').remove();
            $('#registration').prepend('<div class="alert alert-danger id="message-box">' + errorGlyph + 'Please enter a valid phone number</div>');
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

app.controller('profileCtrl', function($http, $window, jwtHelper, $scope) {
    var init = function() {
        var token = $window.sessionStorage.id_token;
        if (token) {
            initializeFromToken($scope, $window.sessionStorage.id_token, jwtHelper); // function in auth.js
        }
    };
    init();

    $scope.user = {};
    $scope.bookings = [];

    $http.get('api/user/')
        .success(function(data) {
            console.log(data);
            $scope.user = data;
        })
        .error(function(data) {
            console.log(data);
            console.log("handle this error");
        });

    $http.get('api/booking/')
        .success(function(data) {
            $scope.bookings = data;
            console.log(data);
        })
        .error(function(data) {
            console.log(data);
        });

    $scope.deleteBooking = function(bookingID) {
        $http.delete('api/booking/' + bookingID)
            .success(function() {
                console.log("success");
            }).error(function() {
                console.log("error");
            });
    }
});

app.controller('profileUpdateCtrl', function($scope, $http) {
    $scope.update = function(user) {
        $('#registration .alert').remove();
        //validation
        var re = /\D/
        if (user.firstName == undefined || user.lastName == undefined || user.email == undefined) {
            $('#registration .alert').remove();
            $('#registration').prepend('<div class="alert alert-danger id="message-box">' + errorGlyph + 'Please fill all the fields with a valid input</div>');
            return;
        } else if (re.test(user.phone)) {
            $('#registration .alert').remove();
            $('#registration').prepend('<div class="alert alert-danger id="message-box">' + errorGlyph + 'Please enter a valid phone number</div>');
            return;
        }
        $http.put('api/user/update', user)
            .success(function(data, status) {
                var dataBool = status === 200;
                $('#registration .alert').remove();
                $('#registration').prepend('<div class="alert ' + (dataBool ? 'alert-success' : 'alert-danger') + '" id="message-box">' + (dataBool ? successGlyph : errorGlyph) + (dataBool ? 'Success' : 'Failed to update') + '</div>');
                if (dataBool) {
                    $('#createUserForm').remove();
                }
            });
    };
});

app.controller('adminCtrl', function($http, $window, jwtHelper, $scope) {
    var init = function() {
        var token = $window.sessionStorage.id_token;
        if (token) {
            initializeFromToken($scope, $window.sessionStorage.id_token, jwtHelper); // function in auth.js
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
    $scope.bookings = [];
    $http.get('api/booking/' + $scope.username)
        .success(function(data) {
            $scope.bookings = data;
            console.log(data);
        })
        .error(function(data) {
            console.log(data);
        });
    $scope.deleteBooking = function(bookingID) {
        $http.delete('api/booking/' + bookingID)
            .success(function() {
                console.log("success");
            }).error(function() {
                console.log("error");
            });
    }
});
