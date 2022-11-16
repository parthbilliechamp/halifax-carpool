package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;

import java.util.List;

public interface IRideRequest {

    boolean createRideRequest(RideRequest rideRequest, IRideRequestsDao rideRequestsDao);

    List<RideRequest> viewRideRequests(int userId);

    void cancelRideRequest(int rideId);

}
