package com.halifaxcarpool.driver.business.beans;

public class Ride {

    public int rideId;
    public String startLocation;
    public String endLocation;

    @Override
    public String toString() {
        return "Ride{" +
                "rideId=" + rideId +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                '}';
    }

}
