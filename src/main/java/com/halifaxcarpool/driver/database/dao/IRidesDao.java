package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IRidesDao {

    void createNewRide(Ride ride);

    List<Ride> getRides(int driverId);

    Ride getRide(int rideId);

    void insertRideNodes(List<RideNode> rideNodes);
}
