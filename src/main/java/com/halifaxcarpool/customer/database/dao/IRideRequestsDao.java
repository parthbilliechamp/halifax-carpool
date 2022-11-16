package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;

public interface IRideRequestsDao {

    public boolean insertRideRequest(RideRequest rideRequest);
}
