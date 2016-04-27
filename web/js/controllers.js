var app = angular.module('bonierControllers', [])

app.controller('resultListCtrl', function($scope) {
    $scope.testAirline = {
        "airline": "AngularJS Airline",
        "flights": [{
            "flightID": "2214-1459418400000",
            "numberOfSeats": 3,
            "date": "2016-03-31T06:00:00.000Z",
            "totalPrice": 225.0,
            "traveltime": 60,
            "origin": "CPH",
            "destination": "SXF",
            "flightNumber": "COL2214"
        }, {
            "flightID": "2216-1459450800000",
            "numberOfSeats": 3,
            "date": "2016-03-31T15:00:00.000Z",
            "totalPrice": 210.0,
            "traveltime": 60,
            "origin": "CPH",
            "destination": "SXF",
            "flightNumber": "COL2216"
        }, {
            "flightID": "3256-1459432800000",
            "numberOfSeats": 3,
            "date": "2016-03-31T10:00:00.000Z",
            "totalPrice": 195.0,
            "traveltime": 90,
            "origin": "CPH",
            "destination": "STN",
            "flightNumber": "COL3256"
        }, {
            "flightID": "3256-1459465200000",
            "numberOfSeats": 3,
            "date": "2016-03-31T19:00:00.000Z",
            "totalPrice": 150.0,
            "traveltime": 90,
            "origin": "CPH",
            "destination": "STN",
            "flightNumber": "COL3256"
        }]
    };
});

app.controller('searchCtrl', function($scope, $http) {
    var testAirlineUrlBase = "http://angularairline-plaul.rhcloud.com/api/flightinfo/"

    $scope.test = function() {
        console.log("asdf");
    }

    $scope.search = function() {
        console.log($scope.date);
        var isoDate = new Date($scope.date).toISOString();
        var airlineUrl = testAirlineUrlBase + $scope.from + '/' + $scope.to + '/' + isoDate + '/' + $scope.seats
        console.log(airlineUrl);
        $http.get(airlineUrl)
        // $http({url: airlineUrl,
        //         headers: {
        //             'Access-Control-Allow-Credentials': '*',
        //             'withCredentials': true
        //         },
        //         method: 'GET'
        //     })
            .success(function(data, status) {
                console.log(data);
            })
            .error(function(data, status) {
                console.log("unhandled error");
            });
    };
});

app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);
