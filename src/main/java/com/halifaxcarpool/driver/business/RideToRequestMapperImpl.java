package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public class RideToRequestMapperImpl implements IRideToRequestMapper {
    private static final String STATUS = "PENDING";

    @Override
    public boolean sendRideRequest(int rideId, int rideRequestId, double amount,
                                IRideToRequestMapperDao rideToRequestMapperDao) {
        return rideToRequestMapperDao.insertRideToRequestMapper(rideId, rideRequestId, STATUS, amount);
    }

    @Override
    public List<RideRequest> viewReceivedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao) {
        return rideToRequestMapperDao.viewReceivedRequests(rideId);
    }

    @Override
    public List<RideRequest> viewApprovedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao) {
        return rideToRequestMapperDao.viewRidePassengers(rideId);
    }

    @Override
    public boolean updateRideRequestStatus(int rideId, int rideRequestId, String status,
                                           IRideToRequestMapperDao rideToRequestMapperDao) {
        return rideToRequestMapperDao.updateRideRequestStatus(rideId, rideRequestId, status);
    }

    @Override
    public double getPaymentAmount(int rideId, int rideRequestId,
                                   IRideToRequestMapperDao rideToRequestMapperDao) {
        return rideToRequestMapperDao.getPaymentAmount(rideId, rideRequestId);
    }

}
