package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.RideImpl;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public class RecommendationEngine {

    public List<Ride> findDirectRouteRidesFor(RideRequest rideRequest) {
        int customer = rideRequest.customerId;
        IRide ride = new RideImpl();
        List<Ride> rideList = ride.getAllRides();
        //recommendation algorithm to be implemented here
        return null;
    }
}
