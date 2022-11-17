package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public class RecommendationEngine {

    public List<Ride> findRidesFor(RideRequest rideRequest) {
        RideFinder rideFinder = new DirectRouteRideFinder();
        RideFinderDecorator rideFinderDecorator = new MultiRouteRideFinderDecorator(rideFinder);
        return rideFinderDecorator.findMatchingRides(rideRequest);
    }

}
