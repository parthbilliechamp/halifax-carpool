package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public class RideToRequestMapperImpl implements IRideToRequestMapper {

    @Override
    public void sendRideRequest(int rideId, int rideRequestId,
                                IRideToRequestMapperDao rideToRequestMapperDao) {
        String status = "PENDING";
        rideToRequestMapperDao.insertRideToRequestMapper(rideId, rideRequestId, status);
    }

    @Override
    public List<RideRequest> viewReceivedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao) {
        return rideToRequestMapperDao.viewReceivedRequests(rideId);
    }

}
