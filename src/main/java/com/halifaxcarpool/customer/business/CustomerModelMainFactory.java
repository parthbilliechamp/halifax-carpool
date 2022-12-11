package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.recommendation.DirectRouteRideFinder;
import com.halifaxcarpool.customer.business.recommendation.BaseRideFinder;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.driver.business.RideToRequestMapperImpl;

public class CustomerModelMainFactory implements CustomerModelFactory {

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

}
