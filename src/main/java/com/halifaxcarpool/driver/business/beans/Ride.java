package com.halifaxcarpool.driver.business.beans;

import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.IRideNode;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.List;
import java.util.stream.Collectors;

public class Ride implements IRide {

    private int rideId;
    private int driverId;
    private String startLocation;
    private String endLocation;
    private int seatsOffered;
    private int rideStatus;

    public Driver driver;

    public double fare;

    public long paymentId;

    public Ride(){
        this.rideStatus = 0;
    }

    public Ride(int rideId, int driverId, String startLocation, String endLocation, int seatsOffered,
                int rideStatus) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.seatsOffered = seatsOffered;
        this.rideStatus = rideStatus;
    }

    @Override
    public boolean createNewRide(IRidesDao ridesDao, IRideNodeDao rideNodeDao,
                                 IDirectionPointsProvider directionPointsProvider,
                                 IRideNode rideNode) {
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
    public List<Ride> viewAllRides(int driverId, IRidesDao ridesDao) {
        List<Ride> rides = ridesDao.getRides(driverId);
        return rides.stream().filter((x) -> 0 == x.getRideStatus() ||
                1 == x.getRideStatus()).collect(Collectors.toList());
    }

    @Override
    public List<Ride> viewRidesHistory(int driverId, IRidesDao ridesDao) {
        List<Ride> rides = ridesDao.getRides(driverId);
        return rides.stream().filter((x) -> 2 == x.getRideStatus()).collect(Collectors.toList());
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

    public void setRideStatus(int rideStatus) {
        this.rideStatus = rideStatus;
    }

    public void withDriver(Driver driver) {
        this.driver = driver;
    }

    public void withFare(double fare) {
        this.fare = fare;
    }

    public void withPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }


    public void setSeatsOffered(int seatsOffered) {
        this.seatsOffered = seatsOffered;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }


}
