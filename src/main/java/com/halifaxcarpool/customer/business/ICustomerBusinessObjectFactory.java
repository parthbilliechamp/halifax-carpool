package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.recommendation.BaseRideFinder;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;

public interface ICustomerBusinessObjectFactory {

    IRideRequest getRideRequest();

    BaseRideFinder getDirectRouteRideFinder();

    IRideToRequestMapper getRideToRequestMapper();


}
