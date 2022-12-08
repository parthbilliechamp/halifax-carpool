package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public class DirectRouteRideFinder extends RideFinder {

    RideFinderStrategy rideFinderStrategy = new DirectRouteRideFinderStrategy();
    RideFinderNavigator navigator = new RideFinderNavigator(rideFinderStrategy);

    @Override
    public List<List<Ride>> findMatchingRides(RideRequest rideRequest) {
        return navigator.findMatchingRides(rideRequest);
    }

}
