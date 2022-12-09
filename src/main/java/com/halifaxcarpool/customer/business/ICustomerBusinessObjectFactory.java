package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.authentication.ICustomerLogin;
import com.halifaxcarpool.customer.business.recommendation.RideFinder;
import com.halifaxcarpool.customer.business.registration.ICustomerRegistration;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;

public interface ICustomerBusinessObjectFactory {

    ICustomerLogin getCustomerLogin();

    ICustomerAuthentication getCustomerAuthentication();

    ICustomerRegistration getCustomerRegistration();

    IRideRequest getRideRequest();

    RideFinder getDirectRouteRideFinder();

    IRideToRequestMapper getRideToRequestMapper();


}
