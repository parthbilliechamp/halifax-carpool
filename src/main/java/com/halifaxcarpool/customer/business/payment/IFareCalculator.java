package com.halifaxcarpool.customer.business.payment;

import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public interface IFareCalculator {

    double calculateFair(int rideId, int rideRequestId , IRideRequestsDao rideRequestsDao,
                         IRidesDao ridesDao, IDirectionPointsProvider directionPointsProvider);
    double calculateFinalAmount();
    void calculateDeduction();
}
