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

}
