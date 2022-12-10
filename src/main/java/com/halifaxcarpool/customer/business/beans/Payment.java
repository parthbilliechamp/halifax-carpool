package com.halifaxcarpool.customer.business.beans;


public class Payment {
    int paymentId;
    int rideId;
    int customerId;
    int driverId;
    double amountDue;
    String status;

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
}
