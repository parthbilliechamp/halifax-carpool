package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;

import java.util.List;

public class RideRequestImpl implements IRideRequest {

    @Override
    public boolean createRideRequest(RideRequest rideRequest, IRideRequestsDao rideRequestsDao) {
        return rideRequestsDao.insertRideRequest(rideRequest);
    }

    @Override
    public List<RideRequest> viewRideRequests(int userId) {
        return null;
    }

    @Override
    public void cancelRideRequest(int rideId) {

    }

}
