package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public class RideToRequestMapperImpl implements IRideToRequestMapper {
    private static final String STATUS = "PENDING";

    @Override
    public void sendRideRequest(int rideId, int rideRequestId,
                                IRideToRequestMapperDao rideToRequestMapperDao) {
        rideToRequestMapperDao.insertRideToRequestMapper(rideId, rideRequestId, STATUS);
    }

    @Override
    public List<RideRequest> viewReceivedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao) {
        return rideToRequestMapperDao.viewReceivedRequests(rideId);
    }

}
