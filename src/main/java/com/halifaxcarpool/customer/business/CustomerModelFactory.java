package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.business.recommendation.*;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;

public interface CustomerModelFactory {

    User getCustomer();

    IRideRequest getRideRequest();

    BaseRideFinder getDirectRouteRideFinder();

    IRideToRequestMapper getRideToRequestMapper();

    DirectRouteRideFinderStrategy getDirectRideFinderStrategy();

    MultipleRouteRideFinderStrategy getMultiRouteRideFinderStrategy();

    RideFinderNavigator getRideFinderNavigator(RideFinderStrategy rideFinderStrategy);

    RideFinderFacade getRideFinderFacade();


}
