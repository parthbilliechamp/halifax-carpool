package com.halifaxcarpool.customer.business.beans;

public class RideRequestNode {
    private final double latitude;
    private final double longitude;
    private final int rideRequestId;

    public RideRequestNode(double latitude, double longitude, int rideRequestId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rideRequestId = rideRequestId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRideRequestId() {
        return rideRequestId;
    }

}
