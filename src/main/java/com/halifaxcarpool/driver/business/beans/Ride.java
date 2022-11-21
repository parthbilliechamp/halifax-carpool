package com.halifaxcarpool.driver.business.beans;

public class Ride {

    public int rideId;

    public int driverId;
    public String startLocation;
    public String endLocation;

    public int seatsOffered;

    public int rideStatus;

    public String dateTime;

    public Ride(int rideId, int driverId, String startLocation, String endLocation, int seatsOffered,
                int rideStatus, String dateTime) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.seatsOffered = seatsOffered;
        this.rideStatus = rideStatus;
        this.dateTime = dateTime;
    }

}
