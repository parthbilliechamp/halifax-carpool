package com.halifaxcarpool.driver.presentation;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public class DriverUI {

    public String displayRides(List<Ride> rideList) {
        StringBuilder builder = new StringBuilder();
        for (Ride ride: rideList) {
            builder.append(ride);
        }
        return builder.toString();
    }

}
