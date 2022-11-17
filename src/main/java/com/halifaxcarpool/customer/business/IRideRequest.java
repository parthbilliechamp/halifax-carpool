package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public interface IRideRequest {

    void createRideRequest(RideRequest rideRequest);

    List<RideRequest> viewRideRequests(int customerId);

    void cancelRideRequest(int rideId);

}
