package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.recommendation.DirectRouteRideFinder;
import com.halifaxcarpool.customer.business.recommendation.RideFinder;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.driver.business.RideToRequestMapperImpl;

public class CustomerBusinessObjectFactoryMain implements ICustomerBusinessObjectFactory {

    @Override
    public IRideRequest getRideRequest() {
        return new RideRequestImpl();
    }

    @Override
    public RideFinder getDirectRouteRideFinder() {
        return new DirectRouteRideFinder();
    }

    @Override
    public IRideToRequestMapper getRideToRequestMapper() {
        return new RideToRequestMapperImpl();
    }

}
