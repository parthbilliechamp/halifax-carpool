package com.halifaxcarpool.customer.business.beans;

public class RideRequestNode {
    public double latitude;
    public double longitude;
    public int rideRequestId;

    public RideRequestNode(double latitude, double longitude, int rideRequestId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rideRequestId = rideRequestId;
    }

}
