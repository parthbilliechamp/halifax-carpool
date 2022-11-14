package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IViewRidesDao;
import com.halifaxcarpool.driver.database.dao.ViewRidesDaoImpl;

import java.util.List;

public class RideImpl implements IRide {

    IViewRidesDao viewRidesDao = new ViewRidesDaoImpl();

    @Override
    public List<Ride> getAllRides() {
        return viewRidesDao.getAllRides();
    }

    @Override
    public List<Ride> getAllRidesFor(int driverId) {
        return viewRidesDao.getAllRidesForDriver(driverId);
    }

}
