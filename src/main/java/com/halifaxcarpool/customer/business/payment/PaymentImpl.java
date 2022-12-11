package com.halifaxcarpool.customer.business.payment;
import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;
import java.util.Random;

public class PaymentImpl implements  IPayment{

    public static final String STATUS = "NOT_INITIATED";
    public PaymentImpl(){

    }
    @Override
    public void insertPaymentDetails(int rideId, int rideRequestId, IPaymentDao paymentDao,
                                     IRidesDao ridesDao, IRideRequestsDao rideRequestsDao, IRideToRequestMapperDao rideToRequestMapperDao) {
            Ride ride = ridesDao.getRide(rideId);
            int customerId = rideRequestsDao.getCustomerId(rideRequestId);
            Random random = new Random();
            double amount = rideToRequestMapperDao.getPaymentAmount(rideId, rideRequestId);
            int paymentId = random.nextInt(100000);
            Payment payment = new Payment(paymentId,rideId, customerId, ride.getDriverId(), amount, STATUS);
            System.out.println("Payment: "+paymentId +","+ rideId+","+customerId+","+ride.getDriverId()+","+amount);
            paymentDao.insertPaymentRecord(payment);
    }

    @Override
    public List<Payment> getCustomerRideHistory(int customerId, IPaymentDao paymentDao) {
        List<Payment> ridesPayment = paymentDao.getCustomerRidePaymentList(customerId);
        return ridesPayment;
    }

    @Override
    public double getAmountDue(int paymentId, IPaymentDao paymentDao) {
        return paymentDao.getAmountDue(paymentId);
    }

    @Override
    public void updatePaymentStatusToSuccess(int paymentId, IPaymentDao paymentDao) {
        paymentDao.updatePaymentStatus(paymentId);
    }

}
