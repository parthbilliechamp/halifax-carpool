package com.halifaxcarpool.customer.business.beans;


public class RideRequest {

    public int rideRequestId;
    public int customerId;
    public String startLocation;
    public String endLocation;

    public RideRequest(int rideRequestId, int customerId, String startLocation, String endLocation) {
        this.rideRequestId = rideRequestId;
        this.customerId = customerId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public RideRequest() {
        //this.rideRequestId = 6;
        //this.customerId = 1;
    }

    public int getRideRequestId() {
        return rideRequestId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setRideRequestId(int rideRequestId) {
        this.rideRequestId = rideRequestId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    @Override
    public String toString() {
        return "RideRequest{" +
                "rideRequestId=" + rideRequestId +
                ", customerId=" + customerId +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                '}';
    }

}
