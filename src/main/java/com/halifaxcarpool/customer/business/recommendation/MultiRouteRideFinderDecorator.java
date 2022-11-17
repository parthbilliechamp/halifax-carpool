package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.List;

public class MultiRouteRideFinderDecorator extends RideFinderDecorator {

    RideFinderFacade rideFinderFacade = new RideFinderFacade();

    MultiRouteRideFinderDecorator(RideFinder rideFinder) {
        super(rideFinder);
    }

    @Override
    List<Ride> findMatchingRides(RideRequest rideRequest) {
        List<Ride> rideList = new ArrayList<>(super.rideFinder.findMatchingRides(rideRequest));
        List<Ride> multiRouteRides = rideFinderFacade.findMultiRouteRides(rideRequest);
        rideList.addAll(multiRouteRides);
        return rideList;
    }

}
