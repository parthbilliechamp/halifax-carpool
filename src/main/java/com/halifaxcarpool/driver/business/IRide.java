package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public interface IRide {

    boolean createNewRide(Ride ride, IRidesDao ridesDao);

    List<Ride> viewRides(int driverId, IRidesDao ridesDao);

    Ride getRide(int rideId, IRidesDao ridesDao);

    void startRide(int rideId, IRidesDao ridesDao);
    void stopRide(int rideId, IRidesDao ridesDao);

}
