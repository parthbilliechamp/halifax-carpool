package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public class RideImpl implements IRide {

    @Override
    public boolean createNewRide(Ride ride, IRidesDao ridesDao) {
        return ridesDao.createNewRide(ride);
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
    public void cancelRide(int rideId, IRidesDao ridesDao) {
        ridesDao.cancelRide(rideId);
    }

}
