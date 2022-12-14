package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.business.ICustomerDaoFactory;
import com.halifaxcarpool.customer.business.CustomerDaoFactory;
import com.halifaxcarpool.customer.business.ICustomerModelFactory;
import com.halifaxcarpool.customer.business.CustomerModelFactory;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public class MultipleRouteRideFinderStrategy implements RideFinderStrategy {

    ICustomerModelFactory customerModelFactory = new CustomerModelFactory();
    ICustomerDaoFactory customerDaoFactory = new CustomerDaoFactory();
    RideFinderFacade rideFinderFacade = customerModelFactory.getRideFinderFacade();
    IRideNodeDao rideNodeDao = customerDaoFactory.getRideNodeDao();
    IRidesDao ridesDao = customerDaoFactory.getRidesDao();
    IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();

    @Override
    public List<List<Ride>> findMatchingRides(RideRequest rideRequest) {
        return rideFinderFacade.findMultipleRouteRides(rideRequest, directionPointsProvider, rideNodeDao, ridesDao);
    }

}
