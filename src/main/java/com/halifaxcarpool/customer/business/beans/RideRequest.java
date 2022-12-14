package com.halifaxcarpool.customer.business.beans;


import com.halifaxcarpool.customer.business.IRideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;

import java.util.List;

public class RideRequest implements IRideRequest {

    private int rideRequestId;
    private int customerId;
    private String startLocation;
    private String endLocation;

    public RideRequest(int rideRequestId, int customerId, String startLocation, String endLocation) {
        this.rideRequestId = rideRequestId;
        this.customerId = customerId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public RideRequest() {

    }

    @Override
    public void createRideRequest(IRideRequestsDao rideRequestsDao) {
        rideRequestsDao.insertRideRequest(this);
    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId, IRideRequestsDao rideRequestsDao) {
        return rideRequestsDao.viewRideRequests(customerId);
    }

    @Override
    public void cancelRideRequest(RideRequest rideRequest, IRideRequestsDao rideRequestsDao) {
        rideRequestsDao.cancelRideRequest(rideRequest);
    }

    public int getRideRequestId() {
        return rideRequestId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setRideRequestId(int rideRequestId) {
        this.rideRequestId = rideRequestId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    @Override
    public String toString() {
        return "RideRequest{" +
                "rideRequestId=" + rideRequestId +
                ", customerId=" + customerId +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                '}';
    }

}
