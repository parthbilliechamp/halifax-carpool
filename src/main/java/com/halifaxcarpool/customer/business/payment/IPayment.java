package com.halifaxcarpool.customer.business.payment;

import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public interface IPayment {

    void insertPaymentDetails(int rideId, int rideRequestId, IPaymentDao paymentDao,
                              IRidesDao ridesDao, IRideRequestsDao rideRequestsDao,
                              IRideToRequestMapperDao rideToRequestMapperDao);

    List<Payment> getCustomerRideHistory(int customerId, IPaymentDao paymentDao);

    double getAmountDue(int paymentId, IPaymentDao paymentDao);

    boolean updatePaymentStatusToSuccess(int paymentId, IPaymentDao paymentDao);

    boolean driverUpdatePaymentStatus(int paymentId, IPaymentDao paymentDao);

    Payment fetchPaymentDetails(int customerId, int rideId, int driverId, IPaymentDao paymentDao);
}
