package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IRidesDao {

    boolean createRide(Ride ride);

    List<Ride> getRides(int driverId);
}
