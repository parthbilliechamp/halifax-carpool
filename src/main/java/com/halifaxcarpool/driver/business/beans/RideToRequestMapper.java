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

    public RideToRequestMapper(int rideId, int rideRequestId, String status, Double fairPrice){
        this.rideId = rideId;
        this.rideRequestId = rideRequestId;
        this. status = status;
        this.fairPrice = fairPrice;
    }

    public int getRideRequestId() {
        return rideRequestId;
    }

    public void setRideRequestId(int rideRequestId) {
        this.rideRequestId = rideRequestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public double getFairPrice(){
        return this.fairPrice;
    }
    public void setFairPrice(double fairPrice){
        this.fairPrice = fairPrice;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }
}
