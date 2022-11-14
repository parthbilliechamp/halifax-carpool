package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public class RideRecommendationImpl implements IRideRecommendation {

    @Override
    public List<Ride> getDirectRouteRides(RideRequest rideRequest) {
        return null;
    }

    @Override
    public List<Ride> getMultiTransitRouteRides(RideRequest rideRequest) {
        return null;
    }

}
