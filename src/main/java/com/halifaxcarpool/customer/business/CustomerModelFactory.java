package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.business.recommendation.BaseRideFinder;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;

public interface CustomerModelFactory {

    User getCustomer();

    IRideRequest getRideRequest();

    BaseRideFinder getDirectRouteRideFinder();

    IRideToRequestMapper getRideToRequestMapper();

}
