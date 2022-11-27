package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.List;

public class MultiRouteRideFinderDecorator extends RideFinderBaseDecorator {

    RideFinderStrategy rideFinderStrategy = new DirectRouteRideFinderStrategy();
    RideFinderNavigator navigator = new RideFinderNavigator(rideFinderStrategy);

    public MultiRouteRideFinderDecorator(RideFinder rideFinder) {
        super(rideFinder);
    }

    @Override
    public List<Ride> findMatchingRides(RideRequest rideRequest) {
        List<Ride> rideList = new ArrayList<>(super.rideFinder.findMatchingRides(rideRequest));
        List<Ride> multiRouteRides = navigator.findMatchingRides(rideRequest);
        rideList.addAll(multiRouteRides);
        return rideList;
    }

}
