package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IRidesDao {

    boolean createNewRide(Ride ride);

    List<Ride> getRides(int driverId);

    List<Ride> getActiveRides(int customerId);

    Ride getRide(int rideId);
    boolean startRide(int rideId);
    boolean stopRide(int rideId);

    boolean cancelRide(int rideId);

}
