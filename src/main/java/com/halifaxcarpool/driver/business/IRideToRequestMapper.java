package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public interface IRideToRequestMapper {

    void sendRideRequest(int rideId, int rideRequestId, double amount, IRideToRequestMapperDao rideToRequestMapperDao);

    List<RideRequest> viewReceivedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao);
}
