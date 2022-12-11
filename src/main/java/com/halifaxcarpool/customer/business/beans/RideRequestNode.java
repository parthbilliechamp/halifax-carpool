package com.halifaxcarpool.customer.business.beans;

public class RideRequestNode {
    private double latitude;
    private double longitude;
    private int rideRequestId;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRideRequestId() {
        return rideRequestId;
    }

    public void setRideRequestId(int rideRequestId) {
        this.rideRequestId = rideRequestId;
    }

    public RideRequestNode(double latitude, double longitude, int rideRequestId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rideRequestId = rideRequestId;
    }

}
