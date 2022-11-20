package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public interface IRide {

    boolean createRide(Ride ride, IRidesDao ridesDao);

    List<Ride> viewRides(int driverId, IRidesDao ridesDao);

}
