package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.ICustomerModelFactory;
import com.halifaxcarpool.customer.business.CustomerModelFactory;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleRouteRideFinderDecorator extends RideFinderBaseDecorator {

    ICustomerModelFactory customerModelFactory = new CustomerModelFactory();
    RideFinderStrategy rideFinderStrategy = customerModelFactory.getMultiRouteRideFinderStrategy();
    RideFinderNavigator navigator = customerModelFactory.getRideFinderNavigator(rideFinderStrategy);

    public MultipleRouteRideFinderDecorator(BaseRideFinder rideFinder) {
        super(rideFinder);
    }

    @Override
    public List<List<Ride>> findMatchingRides(RideRequest rideRequest) {
        List<List<Ride>> recommendedRides = new ArrayList<>(super.rideFinder.findMatchingRides(rideRequest));
        List<List<Ride>> multipleRouteRides = navigator.findMatchingRides(rideRequest);
        recommendedRides.addAll(multipleRouteRides);
        return filterOutCompletedRides(recommendedRides);
    }

    private List<List<Ride>> filterOutCompletedRides(List<List<Ride>> rides) {
        return rides.stream()
                .filter(rideList -> rideList.stream()
                        .allMatch(ride -> 1 == ride.getRideStatus() || 0 == ride.getRideStatus()))
                .collect(Collectors.toList());
    }

}
