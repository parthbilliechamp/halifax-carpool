package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.List;

public class MultipleRouteRideFinderDecorator extends RideFinderBaseDecorator {

    RideFinderStrategy rideFinderStrategy = new MultipleRouteRideFinderStrategy();
    RideFinderNavigator navigator = new RideFinderNavigator(rideFinderStrategy);

    public MultipleRouteRideFinderDecorator(BaseRideFinder rideFinder) {
        super(rideFinder);
    }

    @Override
    public List<List<Ride>> findMatchingRides(RideRequest rideRequest) {
        List<List<Ride>> recommendedRides = new ArrayList<>(super.rideFinder.findMatchingRides(rideRequest));
        List<List<Ride>> multipleRouteRides = navigator.findMatchingRides(rideRequest);
        recommendedRides.addAll(multipleRouteRides);
        return recommendedRides;
    }


}
