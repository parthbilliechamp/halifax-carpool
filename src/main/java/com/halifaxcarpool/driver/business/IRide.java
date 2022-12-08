package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public interface IRide {

    boolean createNewRide(Ride ride, IRidesDao ridesDao,
                          IRideNodeDao rideNodeDao, IDirectionPointsProvider directionPointsProvider);

    List<Ride> viewRides(int driverId, IRidesDao ridesDao);

    Ride getRide(int rideId, IRidesDao ridesDao);

    boolean cancelRide(int rideId, IRidesDao ridesDao);

}
