package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoMockImpl;

import java.util.List;

public class RideRequestMockImpl implements IRideRequest {

    IRideRequestsDao rideRequestsDao = new RideRequestsDaoMockImpl();


    @Override
    public void createRideRequest(RideRequest rideRequest) {

        rideRequestsDao.createRideRequest(rideRequest);

    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId) {
        return rideRequestsDao.viewRideRequests(customerId);
    }

    @Override
    public void cancelRideRequest(int rideId) {

    }

}
