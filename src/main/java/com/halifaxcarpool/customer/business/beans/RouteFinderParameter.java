package com.halifaxcarpool.customer.business.beans;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public class RouteFinderParameter {

    private final RideRequest rideRequest;
    private final IRideNodeDao rideNodeDao;
    private final IRidesDao ridesDao;
    private final LatLng startLocationPoint;
    private final LatLng endLocationPoint;

    public RouteFinderParameter(RideRequest rideRequest,
                                IRideNodeDao rideNodeDao,
                                IRidesDao ridesDao,
                                LatLng startLocationPoint,
                                LatLng endLocationPoint) {
        this.rideRequest = rideRequest;
        this.rideNodeDao = rideNodeDao;
        this.ridesDao = ridesDao;
        this.startLocationPoint = startLocationPoint;
        this.endLocationPoint = endLocationPoint;
    }

    public RideRequest getRideRequest() {
        return rideRequest;
    }

    public IRideNodeDao getRideNodeDao() {
        return rideNodeDao;
    }

    public IRidesDao getRidesDao() {
        return ridesDao;
    }

    public LatLng getStartLocationPoint() {
        return startLocationPoint;
    }

    public LatLng getEndLocationPoint() {
        return endLocationPoint;
    }

}
