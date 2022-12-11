package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Payment;

import java.util.List;

public interface IPaymentDao {

    public void insertPaymentRecord(Payment payment);
    public void updatePaymentStatus(int rideId, int customerId);
    public List<Payment> getCustomerRidePaymentList(int customerId);

    public double getAmountDue(int paymentId);

    public void changePaymentStatusSuccess(int paymentId);

}
