package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public class RideFinderNavigator {

    private final RideFinderStrategy rideFinderStrategy;

    public RideFinderNavigator(RideFinderStrategy rideFinderStrategy) {
        this.rideFinderStrategy = rideFinderStrategy;
    }

    public List<List<Ride>> findMatchingRides(RideRequest rideRequest) {
        return rideFinderStrategy.findMatchingRides(rideRequest);
    }

}
