package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;

import java.util.List;

public class RideRequestImpl implements IRideRequest {

    @Override
    public void createRideRequest(RideRequest rideRequest, IRideRequestsDao rideRequestsDao) {
        rideRequestsDao.insertRideRequest(rideRequest);
    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId, IRideRequestsDao rideRequestsDao) {
        return rideRequestsDao.viewRideRequests(customerId);
    }

    @Override
    public void cancelRideRequest(int rideId, IRideRequestsDao rideRequestsDao) {

    }

}
