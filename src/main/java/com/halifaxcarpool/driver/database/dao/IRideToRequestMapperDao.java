package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.List;

public interface IRideToRequestMapperDao {

    void insertRideToRequestMapper(int rideId, int rideRequestId, String status, double amount);

    List<RideRequest> viewReceivedRequests(int rideId);
    List<RideRequest> viewRidePassengers(int rideId);
    void updateRideRequestStatus(int rideId, int rideRequestId, String status);
    void updatePaymentAmount(int rideId, int rideRequestId, double amount);
    double getPaymentAmount(int rideId, int rideRequestId);

}
