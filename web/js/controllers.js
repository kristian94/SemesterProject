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
