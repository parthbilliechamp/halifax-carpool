package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public abstract class BaseRideFinder {

    public abstract List<List<Ride>> findMatchingRides(RideRequest rideRequest);

}
