package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.ICustomerModelFactory;
import com.halifaxcarpool.customer.business.CustomerModelFactory;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.List;

public class DirectRouteRideFinder extends BaseRideFinder {

    ICustomerModelFactory customerModelFactory = new CustomerModelFactory();
    RideFinderStrategy rideFinderStrategy = customerModelFactory.getDirectRideFinderStrategy();
    RideFinderNavigator navigator = customerModelFactory.getRideFinderNavigator(rideFinderStrategy);

    @Override
    public List<List<Ride>> findMatchingRides(RideRequest rideRequest) {
        List<List<Ride>> recommendedRides = new ArrayList<>();
        try {
            recommendedRides = navigator.findMatchingRides(rideRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommendedRides;
    }

}
