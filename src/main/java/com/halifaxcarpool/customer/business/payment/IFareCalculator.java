package com.halifaxcarpool.customer.business.payment;

import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public interface IFareCalculator {

    public double calculateFair(int rideId, IRideRequestsDao rideRequestsDao, IRidesDao ridesDao);
    public double calculateFinalAmount();
    public void calculateDeduction();
}
