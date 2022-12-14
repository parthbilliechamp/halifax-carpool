package com.halifaxcarpool.customer.business.beans;


import com.halifaxcarpool.customer.business.payment.IPayment;
import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;
import java.util.Random;

public class Payment  implements IPayment {
    public static final String STATUS = "NOT_INITIATED";
    private int paymentId;
    private int rideId;
    private int customerId;
    private int driverId;
    private double amountDue;
    private String status;



    public Payment(){

    }
    public Payment(int paymentId, int rideId, int customerId, int driverId, double amountDue, String status){
        this.paymentId = paymentId;
        this.rideId = rideId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.amountDue = amountDue;
        this.status = status;
    }

    public  String getStatus(){
        return  this.status;
    }
    public int getPaymentId(){return this.paymentId;}
    public  int getRideId(){
        return this.rideId;
    }
    public int getDriverId(){
        return this.driverId;
    }
    public  double getAmountDue(){
        return this.amountDue;
    }
    public int getCustomerId(){
        return this.customerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void insertPaymentDetails(int rideId, int rideRequestId, IPaymentDao paymentDao,
                                     IRidesDao ridesDao, IRideRequestsDao rideRequestsDao,
                                     IRideToRequestMapperDao rideToRequestMapperDao) {
        Ride ride = ridesDao.getRide(rideId);

        int rideCustomerId = rideRequestsDao.getCustomerId(rideRequestId);
        double amount = rideToRequestMapperDao.getPaymentAmount(rideId, rideRequestId);
        int newPaymentId = getRandomPaymentId();

        Payment payment = new Payment(newPaymentId,rideId, rideCustomerId, ride.getDriverId(), amount, STATUS);
        paymentDao.insertPaymentRecord(payment);
    }

    @Override
    public List<Payment> getCustomerRideHistory(int customerId, IPaymentDao paymentDao) {
        return paymentDao.getCustomerRidePaymentList(customerId);
    }

    @Override
    public double getAmountDue(int paymentId, IPaymentDao paymentDao) {
        return paymentDao.getAmountDue(paymentId);
    }

    @Override
    public boolean updatePaymentStatusToSuccess(int paymentId, IPaymentDao paymentDao) {
        return paymentDao.changePaymentStatusSuccess(paymentId);
    }

    @Override
    public boolean driverUpdatePaymentStatus(int paymentId, IPaymentDao paymentDao) {
        return paymentDao.driverUpdatePaymentStatus(paymentId);
    }

    @Override
    public Payment fetchPaymentDetails(int customerId, int rideId, int driverId, IPaymentDao paymentDao) {
        return paymentDao.fetchPaymentDetails(customerId,rideId,driverId);
    }

    private int getRandomPaymentId(){
        final int idBound = 1000;
        return new Random().nextInt(idBound);
    }
}
