angular.module("bonierFilters", ['bonierFactories'])
    .filter("dateFilter", function() {
        return function(input) {
            return input;
        };
    })
    .filter("ttimeFilter", function() {
        return function(flights, ttime) {
            ttime = (ttime == undefined) ? 1 : ttime;
            var filtered = [];
            angular.forEach(flights, function(flights) {
                if (ttime <= flights.traveltime) {
                    filtered.push(flights);
                }
            });
            return filtered;
        };
    })
    .filter("priceFilter", function() {
        return function(flights, price) {
            if (price == undefined) return flights;
            var filtered = [];
            angular.forEach(flights, function(flights) {
                if (price >= flights.totalPrice) {
                    filtered.push(flights);
                }
            });
            return filtered;
        };
    })
    //works, but shouldn't actually be necessary since server should return the needed tickets if avaliable?
    .filter("seatFilter", function() {
        return function(flights, requestedSeats) {
            var filtered = [];
            angular.forEach(flights, function(flights) {
                if (requestedSeats <= flights.numberOfSeats) {
                    filtered.push(flights);
                }
            });
            return filtered;
        };
    });
