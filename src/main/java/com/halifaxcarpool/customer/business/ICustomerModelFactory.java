package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.business.payment.FareCalculatorImpl;
import com.halifaxcarpool.customer.business.payment.IFareCalculator;
import com.halifaxcarpool.customer.business.payment.IPayment;
import com.halifaxcarpool.customer.business.recommendation.*;

public interface ICustomerModelFactory {

    User getCustomer();

    IRideRequest getRideRequest();

    BaseRideFinder getDirectRouteRideFinder();

    DirectRouteRideFinderStrategy getDirectRideFinderStrategy();

    MultipleRouteRideFinderStrategy getMultiRouteRideFinderStrategy();

    RideFinderNavigator getRideFinderNavigator(RideFinderStrategy rideFinderStrategy);

    RideFinderFacade getRideFinderFacade();

    IPayment getPayment();

    IFareCalculator getFareCalculator();

    FareCalculatorImpl getFareCalculatorWithParameters(double originalAmount, double discountPercentage);

}
