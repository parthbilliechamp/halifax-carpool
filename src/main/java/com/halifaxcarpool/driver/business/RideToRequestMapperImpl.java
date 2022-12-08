package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public class RideToRequestMapperImpl implements IRideToRequestMapper {

    //the status will always be pending initially when the data nodes are inserted first time.
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
