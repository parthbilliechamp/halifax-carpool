package com.halifaxcarpool.customer.business.payment;

import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public interface IPayment {

    public void insertPaymentDetails(int rideId, int rideRequestId, IPaymentDao paymentDao,
                                     IRidesDao ridesDao, IRideRequestsDao rideRequestsDao, IRideToRequestMapperDao rideToRequestMapperDao);
}
