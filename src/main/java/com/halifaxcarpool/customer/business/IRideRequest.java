package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;

import java.util.List;

public interface IRideRequest {

    void createRideRequest(IRideRequestsDao rideRequestsDao);

    List<RideRequest> viewRideRequests(int customerId, IRideRequestsDao rideRequestsDao);

    void cancelRideRequest(RideRequest rideRequest, IRideRequestsDao rideRequestsDao);


}
