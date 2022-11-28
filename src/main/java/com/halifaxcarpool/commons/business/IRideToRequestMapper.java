package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public interface IRideToRequestMapper {

    void sendRideRequest(int rideId, int rideRequestId, IRideToRequestMapperDao rideToRequestMapperDao);

    List<RideRequest> viewReceivedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao);
}
