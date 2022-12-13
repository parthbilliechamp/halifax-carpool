package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.recommendation.*;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.driver.business.RideToRequestMapperImpl;

public class CustomerModelMainFactory implements CustomerModelFactory {

    @Override
    public User getCustomer() {
        return new Customer();
    }

    @Override
    public IRideRequest getRideRequest() {
        return new RideRequest();
    }

    @Override
    public BaseRideFinder getDirectRouteRideFinder() {
        return new DirectRouteRideFinder();
    }

    @Override
    public IRideToRequestMapper getRideToRequestMapper() {
        return new RideToRequestMapperImpl();
    }

    @Override
    public DirectRouteRideFinderStrategy getDirectRideFinderStrategy() {
        return new DirectRouteRideFinderStrategy();
    }

    @Override
    public MultipleRouteRideFinderStrategy getMultiRouteRideFinderStrategy() {
        return new MultipleRouteRideFinderStrategy();
    }

    @Override
    public RideFinderNavigator getRideFinderNavigator(RideFinderStrategy rideFinderStrategy) {
        return new RideFinderNavigator(rideFinderStrategy);
    }

    @Override
    public RideFinderFacade getRideFinderFacade() {
        return new RideFinderFacade();
    }

}
