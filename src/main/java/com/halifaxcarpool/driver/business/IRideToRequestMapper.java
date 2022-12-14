package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public interface IRideToRequestMapper {

    boolean sendRideRequest(int rideId, int rideRequestId, double amount,
                            IRideToRequestMapperDao rideToRequestMapperDao);

    List<RideRequest> viewReceivedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao);
    List<RideRequest> viewApprovedRequest(int rideId, IRideToRequestMapperDao rideToRequestMapperDao);
    boolean updateRideRequestStatus(int rideId, int rideRequestId, String status,
                                    IRideToRequestMapperDao rideToRequestMapperDao);
    double getPaymentAmount(int rideId, int rideRequestId,
                            IRideToRequestMapperDao rideToRequestMapperDao);
}
