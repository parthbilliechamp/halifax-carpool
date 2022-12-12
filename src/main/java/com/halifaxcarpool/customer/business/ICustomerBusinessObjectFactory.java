package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.recommendation.RideFinder;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;

public interface ICustomerBusinessObjectFactory {

    IRideRequest getRideRequest();

    RideFinder getDirectRouteRideFinder();

    IRideToRequestMapper getRideToRequestMapper();


}
