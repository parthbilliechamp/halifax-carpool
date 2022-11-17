package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public class DirectRouteRideFinder extends RideFinder {

    RideFinderFacade rideFinderFacade = new RideFinderFacade();

    @Override
    public List<Ride> findMatchingRides(RideRequest rideRequest) {
        return rideFinderFacade.findDirectRouteRides(rideRequest);
    }

}
