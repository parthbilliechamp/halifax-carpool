package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.CommonsFactory;
import com.halifaxcarpool.commons.business.CommonsFactoryImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import com.halifaxcarpool.customer.business.ICustomerDaoFactory;
import com.halifaxcarpool.customer.business.CustomerDaoFactory;
import com.halifaxcarpool.customer.business.CustomerModelFactory;
import com.halifaxcarpool.customer.business.CustomerModelMainFactory;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public class DirectRouteRideFinderStrategy implements RideFinderStrategy {

    CustomerModelFactory customerModelFactory = new CustomerModelMainFactory();
    ICustomerDaoFactory customerDaoFactory = new CustomerDaoFactory();

    CommonsFactory commonsFactory = new CommonsFactoryImpl();
    RideFinderFacade rideFinderFacade = customerModelFactory.getRideFinderFacade();
    IRideNodeDao rideNodeDao = customerDaoFactory.getRideNodeDao();
    IGeoCoding geoCoding = commonsFactory.getGeoCoding();
    IRidesDao ridesDao = customerDaoFactory.getRidesDao();

    @Override
    public List<List<Ride>> findMatchingRides(RideRequest rideRequest) {
        return rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao);
    }

}
