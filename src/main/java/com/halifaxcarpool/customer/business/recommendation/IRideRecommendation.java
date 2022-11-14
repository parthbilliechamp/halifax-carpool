package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IRideRecommendation {

    List<Ride> getDirectRouteRides(RideRequest rideRequest);

    //potential feature of the application;
    List<Ride> getMultiTransitRouteRides(RideRequest rideRequest);

}
