package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public class RideImpl implements IRide {

    @Override
    public boolean createNewRide(Ride ride, IRidesDao ridesDao,
                                 IRideNodeDao rideNodeDao,
                                 IDirectionPointsProvider directionPointsProvider,
                                 IRideNode rideNode) {
        boolean isRideCreated = ridesDao.createNewRide(ride);
        if (Boolean.FALSE.equals(isRideCreated)) {
            return false;
        }
        boolean isRideNodeInserted = rideNode.insertRideNodes(ride, rideNodeDao, directionPointsProvider);
        if (Boolean.FALSE.equals(isRideNodeInserted)) {
            cancelRide(ride.getRideId(), ridesDao);
            return false;
        }
        return true;
    }

    //TODO add logic of converting ride_status to active or inactive, sql time to local date time
    @Override
    public List<Ride> viewRides(int driverId, IRidesDao ridesDao) {
        return ridesDao.getRides(driverId);
    }

    @Override
    public List<Ride> viewOngoingRides(int customerId, IRidesDao ridesDao) {
        return ridesDao.getActiveRides(customerId);
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
