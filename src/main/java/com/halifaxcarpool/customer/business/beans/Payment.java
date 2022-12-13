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
    int paymentId;
    int rideId;
    int customerId;
    int driverId;
    double amountDue;

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
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
}
