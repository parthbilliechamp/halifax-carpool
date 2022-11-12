package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IViewRidesDao;
import com.halifaxcarpool.driver.database.dao.ViewRidesDaoImpl;

import java.util.List;

public class ViewRidesImpl implements IViewRides {

    IViewRidesDao viewRidesDao = new ViewRidesDaoImpl();

    @Override
    public List<Ride> getAllRides() {
        return viewRidesDao.getAllRides();
    }

    @Override
    public List<Ride> getRidesByDriver(int driverId) {
        return viewRidesDao.getAllRidesForDriver(driverId);
    }

}
