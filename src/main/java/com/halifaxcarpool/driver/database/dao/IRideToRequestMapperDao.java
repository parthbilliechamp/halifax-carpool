package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public interface IRideToRequestMapperDao {

    boolean insertRideToRequestMapper(int rideId, int rideRequestId, String status, double amount);

    List<RideRequest> viewReceivedRequests(int rideId);
    List<RideRequest> viewRidePassengers(int rideId);
    boolean updateRideRequestStatus(int rideId, int rideRequestId, String status);
    double getPaymentAmount(int rideId, int rideRequestId);

}
