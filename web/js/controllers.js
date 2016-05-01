var app = angular.module('bonierControllers', [])
var baseUrl = "http://localhost:8080/SemesterProject/api/flight"
app.controller('resultListCtrl', function($scope, $http, searchResultFactory) {
    $scope.result = searchResultFactory.getSearchResult();
    $scope.parameters = searchResultFactory.getSearchParameters();

    //make this a service???
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
    $scope.search = function() {
        var isoDate = new Date($scope.date);
        // var isoDate = new Date($scope.date).toISOString();
        var searchParameters = {
            "origin": $scope.from,
            "destination": $scope.to,
            "date": isoDate,
            "numberOfSeats": $scope.seats
        };
        //todo check input before post request

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

app.controller('formController', ['$scope', '$http', function ($scope, $http) {
                $scope.create = function (user) {
                    if (user.userName.length < 1) {
                        $('#view6 .alert').remove();
                        $('#view6').prepend('<div class="alert alert-danger id="message-box">Enter a username</div>');
                        return;
                    } else if (user.password.length < 4) {
                        $('#view6 .alert').remove();
                        $('#view6').prepend('<div class="alert alert-danger id="message-box">Password length needs to be at least 4</div>');
                        return;
                    }
                    var data = {
                        userName: user.userName,
                        password: user.password
                    };
                    $http.post('/SemesterProject/api/createuser', data).success(function (data) {
                        var dataBool = data === "ok";
                        $('#view6 .alert').remove();
                        $('#view6').prepend('<div class="alert ' 
                                + (dataBool ? 'alert-success' : 'alert-danger') 
                                + '" id="message-box">' 
                                + (dataBool ? 'Account created' : 'Username taken') + '</div>');
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
        saveSearchResult:function(data) {
            searchResult = data;
            console.log("saving result " + data);
        },
        getSearchResult:function() {
            console.log("getting search result");
            return searchResult;
        },
        saveSearchParameters:function(data) {
            searchParameters = data;
            console.log("saving parameters " + data)
        },
        getSearchParameters:function(data) {
            console.log("getting search parameters")
            return searchParameters;
        }
    };
});
