package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public class RideToRequestMapperImpl implements IRideToRequestMapper {
    private static final String STATUS = "PENDING";

    @Override
    public void sendRideRequest(int rideId, int rideRequestId, double amount,
                                IRideToRequestMapperDao rideToRequestMapperDao) {
        rideToRequestMapperDao.insertRideToRequestMapper(rideId, rideRequestId, STATUS, amount);
    }

    @Override
    public List<RideRequest> viewReceivedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao) {
        return rideToRequestMapperDao.viewReceivedRequests(rideId);
    }

    @Override
    public List<RideRequest> viewApprovedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao) {
        return  rideToRequestMapperDao.viewRidePassengers(rideId);
    }

}
