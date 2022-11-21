package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;

import java.util.List;

public class RideImpl implements IRide {

    @Override
    public boolean createRide(Ride ride, IRidesDao ridesDao) {
        return false;
    }

    //TODO add logic of converting ride_status to active or inactive, sql time to local date time
    //ASK do we need to create a new entity bean for this
    @Override
    public List<Ride> viewRides(int driverId, IRidesDao ridesDao) {
        return ridesDao.getRides(driverId);
    }

}
