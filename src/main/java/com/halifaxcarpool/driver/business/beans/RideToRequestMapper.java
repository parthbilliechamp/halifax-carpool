package com.halifaxcarpool.driver.business.beans;

public class RideToRequestMapper {
    int rideId;
    int rideRequestId;
    String status;
    double fairPrice;

    public RideToRequestMapper(int rideId, int rideRequestId, String status){
        this.rideId = rideId;
        this.rideRequestId = rideRequestId;
        this. status = status;
    }
    public double getFairPrice(){
        return this.fairPrice;
    }
}
