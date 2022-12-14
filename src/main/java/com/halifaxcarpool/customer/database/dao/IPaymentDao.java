package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Payment;

import java.util.List;

public interface IPaymentDao {

    boolean insertPaymentRecord(Payment payment);

    List<Payment> getCustomerRidePaymentList(int customerId);

    double getAmountDue(int paymentId);

    boolean changePaymentStatusSuccess(int paymentId);

    boolean driverUpdatePaymentStatus(int paymentId);

    Payment fetchPaymentDetails(int customerId, int rideId, int driverId);

}
