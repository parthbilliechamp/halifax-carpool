package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public interface IRideRequestsDao {

    void insertRideRequest(RideRequest rideRequest);

    List<RideRequest> viewRideRequests(int customerId);

    int getRideRequestCount(int rideId);

    int getCustomerId(int rideId);

    void cancelRideRequest(RideRequest rideRequest);

    RideRequest getRideRequest(int rideId);

}
