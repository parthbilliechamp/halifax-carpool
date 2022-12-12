package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Payment;

import java.util.List;

public interface IPaymentDao {

    public boolean insertPaymentRecord(Payment payment);

    public List<Payment> getCustomerRidePaymentList(int customerId);

    public double getAmountDue(int paymentId);

    public boolean changePaymentStatusSuccess(int paymentId);

    public boolean driverUpdatePaymentStatus(int paymentId);

    public Payment fetchPaymentDetails(int customerId, int rideId, int driverId);

}
