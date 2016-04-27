var app = angular.module('bonierControllers', [])

app.controller('resultListCtrl', function($scope, searchResultFactory) {
    $scope.result = searchResultFactory.getSearchResult();
});

app.controller('searchCtrl', function($scope, $http, $location, searchResultFactory) {
    var baseUrl = "http://localhost:8080/SemesterProject/api/flight"
    $scope.search = function() {
        console.log($scope.date);
        var isoDate = new Date($scope.date).toISOString();
        var data = {
            "origin": $scope.from,
            "destination": $scope.to,
            "date": isoDate,
            "numberOfSeats": $scope.seats
        };

        //todo check input before post request
        console.log(baseUrl);
        $http.post(baseUrl, data)
            .success(function(data, status) {
                console.log(data);
                searchResultFactory.saveSearchResult(data);
                $location.url('/flights');
            })
            .error(function(data, status) {
                console.log("unhandled error");
            });
    };
});

app.factory('searchResultFactory', function() {
    var searchResult = {};

    return {
        saveSearchResult:function(data) {
            searchResult = data;
            console.log("saving " + data);
        },
        getSearchResult:function() {
            console.log("getting data");
            return searchResult;
        }
    }
});
