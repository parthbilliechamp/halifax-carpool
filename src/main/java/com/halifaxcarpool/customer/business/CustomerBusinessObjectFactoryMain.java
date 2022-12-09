package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.CustomerAuthenticationImpl;
import com.halifaxcarpool.customer.business.authentication.CustomerLoginImpl;
import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.authentication.ICustomerLogin;
import com.halifaxcarpool.customer.business.recommendation.DirectRouteRideFinder;
import com.halifaxcarpool.customer.business.recommendation.RideFinder;
import com.halifaxcarpool.customer.business.registration.CustomerRegistrationImpl;
import com.halifaxcarpool.customer.business.registration.ICustomerRegistration;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.driver.business.RideToRequestMapperImpl;

public class CustomerBusinessObjectFactoryMain implements ICustomerBusinessObjectFactory {

    @Override
    public ICustomerLogin getCustomerLogin() {
        return new CustomerLoginImpl();
    }

    @Override
    public ICustomerAuthentication getCustomerAuthentication() {
        return new CustomerAuthenticationImpl();
    }

    @Override
    public ICustomerRegistration getCustomerRegistration() {
        return new CustomerRegistrationImpl();
    }

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
