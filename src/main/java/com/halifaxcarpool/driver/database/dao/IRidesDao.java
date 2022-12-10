package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IRidesDao {

    boolean createNewRide(Ride ride);

    List<Ride> getRides(int driverId);

    Ride getRide(int rideId);


}
