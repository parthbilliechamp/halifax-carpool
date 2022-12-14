package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public interface IRide {

    boolean createNewRide(IRidesDao ridesDao, IRideNodeDao rideNodeDao,
                          IDirectionPointsProvider directionPointsProvider, IRideNode rideNode);

    List<Ride> viewAllRides(int driverId, IRidesDao ridesDao);

    List<Ride> viewRidesHistory(int driverId, IRidesDao ridesDao);

    List<Ride> viewOngoingRides(int customerId, IRidesDao ridesDao);

    Ride getRide(int rideId, IRidesDao ridesDao);

    boolean startRide(int rideId, IRidesDao ridesDao);

    boolean stopRide(int rideId, IRidesDao ridesDao);

    boolean cancelRide(int rideId, IRidesDao ridesDao);

}
