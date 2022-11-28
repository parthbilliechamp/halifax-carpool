package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RideImpl implements IRide {

    IRidesDao ridesDao = new RidesDaoImpl();

    @Override
    public void createNewRide(Ride ride) {
        ridesDao.createNewRide(ride);
    }

    //TODO add logic of converting ride_status to active or inactive, sql time to local date time
    //ASK do we need to create a new entity bean for this
    @Override
    public List<Ride> viewRides(int driverId, IRidesDao ridesDao) {
        return ridesDao.getRides(driverId);
    }

    @Override
    public Ride getRide(int rideId, IRidesDao ridesDao) {
        return ridesDao.getRide(rideId);
    }

    @Override
    public void insertRideNodes(Ride ride) {
        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();
        String startLocation = ride.startLocation;
        String endLocation = ride.endLocation;
        List<LatLng> ridePoints =
                directionPointsProvider.getPointsBetweenSourceAndDestination(startLocation, endLocation);
        List<RideNode> rideNodes = buildRideNodesFrom(ridePoints, ride.rideId);
        ridesDao.insertRideNodes(rideNodes);
    }

    private List<RideNode> buildRideNodesFrom(List<LatLng> ridePoints, int rideId) {
        int sequence = 0;
        Iterator<LatLng> iterator = ridePoints.iterator();
        List<RideNode> rideNodes = new ArrayList<>();
        while (iterator.hasNext()) {
            LatLng ridePoint = iterator.next();
            RideNode rideNode = new RideNode(ridePoint.latitude, ridePoint.longitude, rideId, ++sequence);
            rideNodes.add(rideNode);
        }
        return rideNodes;
    }

}
