package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.beans.RideRequestNode;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.RideImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;

import java.util.*;

public class RideFinderFacade {
    private static final double MAXIMUM_RIDE_THRESHOLD_KM = 0.5;
    private final IRide ride;

    public RideFinderFacade() {
        ride = new RideImpl();
    }

    public List<Ride> findDirectRouteRides(RideRequest rideRequest, IRideNodeDao rideNodeDao,
                                           IGeoCoding geoCoding, IRidesDao ridesDao) {

        LatLng startLocationPoint = geoCoding.getLatLng(rideRequest.startLocation);
        LatLng endLocationPoint = geoCoding.getLatLng(rideRequest.endLocation);
        if (null == startLocationPoint || null == endLocationPoint) {
            throw new RuntimeException("Error finding coordinates of the ride request : " + rideRequest.rideRequestId);
        }

        List<RideNode> rideNodesNearToStartLocation = rideNodeDao.getRideNodes(startLocationPoint);
        List<RideNode> rideNodesNearToEndLocation = rideNodeDao.getRideNodes(endLocationPoint);

        RideRequestNode startNode =
                new RideRequestNode(startLocationPoint.latitude, startLocationPoint.longitude,
                        rideRequest.rideRequestId);
        RideRequestNode endNode =
                new RideRequestNode(endLocationPoint.latitude, endLocationPoint.longitude,
                        rideRequest.rideRequestId);

        Set<RideNode> validRidesForStartNode = new HashSet<>();
        Map<RideNode, RideNode> validRidesForEndNode = new HashMap<>();

        filterValidRidesForStartNode(rideNodesNearToStartLocation, startNode, validRidesForStartNode);
        filterValidRidesForEndNode(rideNodesNearToEndLocation, endNode, validRidesForEndNode);

        validRidesForStartNode.retainAll(validRidesForEndNode.keySet());

        return getRidesBasedOnDirection(validRidesForStartNode, validRidesForEndNode, ridesDao);
    }

    public List<Ride> findMultiRouteRides(RideRequest rideRequest) {
        /**
         * integration with Maps API to find the recommended route
         */
        return null;
    }

    private static void filterValidRidesForStartNode(List<RideNode> rideNodesNearToStartLocation,
                                                     RideRequestNode startNode,
                                                     Set<RideNode> validRidesForStartNode) {
        for (RideNode rideNode: rideNodesNearToStartLocation) {
            double distanceFromStartNode = DistanceFinder.findDistance(startNode.latitude, rideNode.latitude,
                    startNode.longitude, rideNode.longitude);
            if (distanceFromStartNode < MAXIMUM_RIDE_THRESHOLD_KM && !validRidesForStartNode.contains(rideNode)) {
                validRidesForStartNode.add(rideNode);
            }
        }
    }

    private static void filterValidRidesForEndNode(List<RideNode> rideNodesNearToEndLocation, RideRequestNode endNode, Map<RideNode, RideNode> validRidesForEndNode) {
        for (RideNode rideNode: rideNodesNearToEndLocation) {
            double distanceFromEndNode = DistanceFinder.findDistance(endNode.latitude, rideNode.latitude,
                    endNode.longitude, rideNode.longitude);
            if (distanceFromEndNode <= MAXIMUM_RIDE_THRESHOLD_KM && !validRidesForEndNode.containsKey(rideNode)) {
                validRidesForEndNode.put(rideNode, rideNode);
            }
        }
    }

    private List<Ride> getRidesBasedOnDirection(Set<RideNode> validRidesForStartNode,
                                                Map<RideNode, RideNode> validRidesForEndNode,
                                                IRidesDao ridesDao) {
        List<Ride> recommendedRides = new ArrayList<>();
        for (RideNode rideNode: validRidesForStartNode) {
            RideNode temp = validRidesForEndNode.get(rideNode);
            if (rideNode.sequence <= temp.sequence) {
                Ride currentRide = ride.getRide(rideNode.rideId, ridesDao);
                recommendedRides.add(currentRide);
            }
        }
        return recommendedRides;
    }


}
