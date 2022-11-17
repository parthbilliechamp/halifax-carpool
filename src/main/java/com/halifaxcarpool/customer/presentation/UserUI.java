package com.halifaxcarpool.customer.presentation;

import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public class UserUI {

    public String viewRideRequests(List<RideRequest> rideRequests) {
        StringBuilder builder = new StringBuilder();
        for (RideRequest rideRequest: rideRequests) {
            builder.append(rideRequest);
        }
        return builder.toString();
    }
}
