package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.ICustomer;
import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.recommendation.RideFinder;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;

public interface ICustomerBusinessObjectFactory {

    ICustomer getCustomer();

    ICustomerAuthentication getCustomerAuthentication();

    IRideRequest getRideRequest();

    RideFinder getDirectRouteRideFinder();

    IRideToRequestMapper getRideToRequestMapper();


}
