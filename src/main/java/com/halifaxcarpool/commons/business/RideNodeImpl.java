package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RideNodeImpl implements IRideNode {
    @Override
    public void insertRideNodes(Ride ride, IRideNodeDao rideNodeDao, IDirectionPointsProvider directionPointsProvider) {
        String startLocation = ride.startLocation;
        String endLocation = ride.endLocation;
        List<LatLng> ridePoints =
                directionPointsProvider.getPointsBetweenSourceAndDestination(startLocation, endLocation);
        List<RideNode> rideNodes = buildRideNodesFrom(ridePoints, ride.rideId);
        rideNodeDao.insertRideNodes(rideNodes);
    }

    @Override
    public List<RideNode> getRideNodes(LatLng latLng, IRideNodeDao rideNodeDao) {
        return rideNodeDao.getRideNodes(latLng);
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
