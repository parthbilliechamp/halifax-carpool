package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoImpl;

import java.util.List;

public class RideRequestImpl implements IRideRequest {

    IRideRequestsDao rideRequestsDao = new RideRequestsDaoImpl();

    @Override
    public void createRideRequest(RideRequest rideRequest) {

    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId) {
        return rideRequestsDao.viewRideRequests(customerId);
    }

    @Override
    public void cancelRideRequest(int rideId) {

    }

}
