package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;

import java.util.List;

public class RideRequestMockImpl implements IRideRequest {
    @Override
    public void createRideRequest(RideRequest rideRequest, IRideRequestsDao rideRequestsDao) {

    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId, IRideRequestsDao rideRequestsDao) {
        return null;
    }

    @Override
    public void cancelRideRequest(RideRequest rideRequest, IRideRequestsDao rideRequestsDao) {
        rideRequestsDao.cancelRideRequest(rideRequest);
    }
}
