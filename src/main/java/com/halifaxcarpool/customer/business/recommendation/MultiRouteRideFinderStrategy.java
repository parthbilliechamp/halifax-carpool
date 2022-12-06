package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.geocoding.GeoCodingImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.customer.database.dao.RideNodeDaoImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;

import java.util.List;

public class MultiRouteRideFinderStrategy implements RideFinderStrategy {

    RideFinderFacade rideFinderFacade = new RideFinderFacade();
    IRideNodeDao rideNodeDao = new RideNodeDaoImpl();
    IGeoCoding geoCoding = new GeoCodingImpl();
    IRidesDao ridesDao = new RidesDaoImpl();

    @Override
    public List<List<Ride>> findMatchingRides(RideRequest rideRequest) {
        return rideFinderFacade.findMultipleRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
    }

}
