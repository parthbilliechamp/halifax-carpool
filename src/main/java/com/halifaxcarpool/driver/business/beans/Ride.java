package com.halifaxcarpool.driver.business.beans;

import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.IRideNode;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;

public class Ride implements IRide {

    private int rideId;
    private int driverId;
    private String startLocation;
    private String endLocation;
    private int seatsOffered;
    private int rideStatus;
    private String dateTime;

    public Driver driver;

    public double fare;

    public Ride(){
        this.rideStatus = 1;
    }

    public Ride(int rideId, int driverId, String startLocation, String endLocation, int seatsOffered,
                int rideStatus, String dateTime) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.seatsOffered = seatsOffered;
        this.rideStatus = rideStatus;
        this.dateTime = dateTime;
    }

    @Override
    public boolean createNewRide(IRidesDao ridesDao, IRideNodeDao rideNodeDao, IDirectionPointsProvider directionPointsProvider, IRideNode rideNode) {
        boolean isRideCreated = ridesDao.createNewRide(this);
        if (Boolean.FALSE.equals(isRideCreated)) {
            return false;
        }
        boolean isRideNodeInserted = rideNode.insertRideNodes(this, rideNodeDao, directionPointsProvider);
        if (Boolean.FALSE.equals(isRideNodeInserted)) {
            cancelRide(getRideId(), ridesDao);
            return false;
        }
        return true;
    }

    @Override
    public List<Ride> viewRides(int driverId, IRidesDao ridesDao) {
        return ridesDao.getRides(driverId);
    }

    @Override
    public List<Ride> viewOngoingRides(int customerId, IRidesDao ridesDao) {
        return ridesDao.getActiveRides(customerId);
    }

    @Override
    public Ride getRide(int rideId, IRidesDao ridesDao) {
        return ridesDao.getRide(rideId);
    }

    @Override
    public boolean startRide(int rideId, IRidesDao ridesDao) {
        return ridesDao.startRide(rideId);
    }

    @Override
    public boolean stopRide(int rideId, IRidesDao ridesDao) {
        return ridesDao.stopRide(rideId);
    }

    @Override
    public boolean cancelRide(int rideId, IRidesDao ridesDao) {
        return ridesDao.cancelRide(rideId);
    }

    public int getRideId() {
        return rideId;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public int getSeatsOffered() {
        return seatsOffered;
    }

    public int getRideStatus() {
        return rideStatus;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public void setSeatsOffered(int seatsOffered) {
        this.seatsOffered = seatsOffered;
    }

    public void setRideStatus(int rideStatus) {
        this.rideStatus = rideStatus;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void withDriver(Driver driver) {
        this.driver = driver;
    }

    public void withFare(double fare) {
        this.fare = fare;
    }

}
