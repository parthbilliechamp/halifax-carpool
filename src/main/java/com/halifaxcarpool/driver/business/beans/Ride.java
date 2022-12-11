package com.halifaxcarpool.driver.business.beans;

public class Ride {

    private int rideId;
    private int driverId;
    private String startLocation;
    private String endLocation;
    private int seatsOffered;
    private int rideStatus;
    private String dateTime;

    public Ride(){
        this.rideStatus = 1;
    }

    public int getRideId() {
        return rideId;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public int getSeatsOffered() {
        return seatsOffered;
    }

    public int getRideStatus() {
        return rideStatus;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public void setSeatsOffered(int seatsOffered) {
        this.seatsOffered = seatsOffered;
    }

    public void setRideStatus(int rideStatus) {
        this.rideStatus = rideStatus;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

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
