package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public interface IRideToRequestMapperDao {

    void insertRideToRequestMapper(int rideId, int rideRequestId, String status);

    List<RideRequest> viewReceivedRequests(int rideId);

}
