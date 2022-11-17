package com.halifaxcarpool.customer.business.beans;

public class RideRequest {

    //#TODO add fields referring to the database
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

}
