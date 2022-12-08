package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.IRideNode;
import com.halifaxcarpool.commons.business.RideNodeImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.customer.database.dao.RideNodeDaoImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public class RideImpl implements IRide {

    private static final String CREATE_NEW_RIDE_PAGE = "create_new_ride";

    @Override
    public String createNewRide(Ride ride, IRidesDao ridesDao) {
        boolean isRideCreated = ridesDao.createNewRide(ride);
        if (Boolean.FALSE.equals(isRideCreated)) {
            return CREATE_NEW_RIDE_PAGE;
        }
        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();
        IRideNode rideNode = new RideNodeImpl();
        IRideNodeDao rideNodeDao = new RideNodeDaoImpl();
        boolean isRideNodeInserted = rideNode.insertRideNodes(ride, rideNodeDao, directionPointsProvider);
        if (Boolean.FALSE.equals(isRideNodeInserted)) {
            cancelRide(ride.rideId, ridesDao);
        }
        return CREATE_NEW_RIDE_PAGE;
    }

    //TODO add logic of converting ride_status to active or inactive, sql time to local date time
    @Override
    public List<Ride> viewRides(int driverId, IRidesDao ridesDao) {
        return ridesDao.getRides(driverId);
    }

    @Override
    public Ride getRide(int rideId, IRidesDao ridesDao) {
        return ridesDao.getRide(rideId);
    }

    @Override
    public boolean cancelRide(int rideId, IRidesDao ridesDao) {
        return ridesDao.cancelRide(rideId);
    }

}
