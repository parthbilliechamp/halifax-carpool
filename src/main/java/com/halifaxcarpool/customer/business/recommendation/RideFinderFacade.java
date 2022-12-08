package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.beans.RideRequestNode;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.RideImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.*;

public class RideFinderFacade {

    private static class RideLookup {
        int firstRideId;
        int secondRideId;
        RideLookup(int firstRideId, int secondRideId) {
            this.firstRideId = firstRideId;
            this.secondRideId = secondRideId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RideLookup that = (RideLookup) o;
            return firstRideId == that.firstRideId && secondRideId == that.secondRideId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstRideId, secondRideId);
        }
    }
    private static final double MAXIMUM_RIDE_THRESHOLD_KM = 0.2;
    private static final double MAXIMUM_RIDE_THRESHOLD_KM_FOR_TWO_RIDES = 0.2;
    private final IRide ride;

    public RideFinderFacade() {
        ride = new RideImpl();
    }

    public List<List<Ride>> findDirectRouteRides(RideRequest rideRequest, IRideNodeDao rideNodeDao,
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

        List<List<Ride>> recommendedRidesWrapper = new ArrayList<>();
        List<Ride> recommendedRides = getRidesBasedOnDirection(validRidesForStartNode, validRidesForEndNode, ridesDao);
        convertListOfRidesToListOfListOfRides(recommendedRidesWrapper, recommendedRides);
        return recommendedRidesWrapper;
    }

    public List<List<Ride>> findMultipleRouteRides(RideRequest rideRequest, IDirectionPointsProvider directionPointsProvider, IRideNodeDao rideNodeDao, IRidesDao ridesDao) {
        List<LatLng> rideReqPoints = directionPointsProvider.getPointsBetweenSourceAndDestination(rideRequest.startLocation, rideRequest.endLocation);

        LatLng startPointOfRideRequest = rideReqPoints.get(0);

        LatLng endPointOfRideRequest = rideReqPoints.get(rideReqPoints.size() - 1);
        Map<RideLookup, List<Ride>> ridesCache = new HashMap<>();
        for (int i = 1; i < rideReqPoints.size() - 1 && ridesCache.size() <= 3; i+=3) {
            LatLng middleSearchPoint = rideReqPoints.get(i);

            List<Ride> ridesForFirstRoute = getRidesForRoute1(rideRequest, rideNodeDao, ridesDao, startPointOfRideRequest, middleSearchPoint, rideReqPoints);
            List<Ride> ridesForSecondRoute = getRidesForRoute2(rideRequest, rideNodeDao, ridesDao, middleSearchPoint, endPointOfRideRequest, rideReqPoints);

            if (ridesForFirstRoute.size() != 0 && ridesForSecondRoute.size() != 0) {
                for (Ride ride1 : ridesForFirstRoute) {
                    for (Ride ride2 : ridesForSecondRoute) {
                        if (ride1.rideId == ride2.rideId) {
                            continue;
                        }
                        RideLookup rideLookup = new RideLookup(ride1.rideId, ride2.rideId);
                        List<Ride> combinedRides = new ArrayList<>();
                        combinedRides.add(ride1);
                        combinedRides.add(ride2);
                        ridesCache.put(rideLookup, combinedRides);
                    }
                }
            }
        }

        return new ArrayList<>(ridesCache.values());
    }

    private List<Ride> getRidesForRoute1(RideRequest rideRequest, IRideNodeDao rideNodeDao, IRidesDao ridesDao, LatLng startPointOfRideRequest, LatLng middleSearchPoint, List<LatLng> rideReqPoints) {

        List<RideNode> rideNodesNearToStartPoint = rideNodeDao.getRideNodes(startPointOfRideRequest);
        List<RideNode> rideNodesNearToMiddlePoint = rideNodeDao.getRideNodes(middleSearchPoint);
        RideRequestNode startNodeOfRideRequest =
                new RideRequestNode(startPointOfRideRequest.latitude, startPointOfRideRequest.longitude,
                        rideRequest.rideRequestId);
        RideRequestNode middleNodeOfRideRequest =
                new RideRequestNode(middleSearchPoint.latitude, middleSearchPoint.longitude,
                        rideRequest.rideRequestId);
        Set<RideNode> validRidesForStartNode = new HashSet<>();
        Map<RideNode, RideNode> validRidesForMiddleNode = new HashMap<>();
        filterValidRidesForStartNodeForTwoRides(rideNodesNearToStartPoint, startNodeOfRideRequest, validRidesForStartNode);
        filterValidRidesForEndNodeForTwoRides(rideNodesNearToMiddlePoint, middleNodeOfRideRequest, validRidesForMiddleNode);
        validRidesForStartNode.retainAll(validRidesForMiddleNode.keySet());
        return getRidesBasedOnDirection(validRidesForStartNode, validRidesForMiddleNode, ridesDao);
    }

    private List<Ride> getRidesForRoute2(RideRequest rideRequest, IRideNodeDao rideNodeDao, IRidesDao ridesDao, LatLng middleSearchPoint, LatLng endPointOfRideRequest, List<LatLng> rideReqPoints) {

        List<RideNode> rideNodesNearToMiddlePoint = rideNodeDao.getRideNodes(middleSearchPoint);
        List<RideNode> rideNodesNearToEndPoint = rideNodeDao.getRideNodes(endPointOfRideRequest);
        RideRequestNode middleNodeOfRideRequest =
                new RideRequestNode(middleSearchPoint.latitude, middleSearchPoint.longitude,
                        rideRequest.rideRequestId);
        RideRequestNode endNodeOfRideRequest =
                new RideRequestNode(endPointOfRideRequest.latitude, endPointOfRideRequest.longitude,
                        rideRequest.rideRequestId);
        Set<RideNode> validRidesForMiddleNode2 = new HashSet<>();
        Map<RideNode, RideNode> validRidesForEndNode = new HashMap<>();
        filterValidRidesForStartNodeForTwoRides(rideNodesNearToMiddlePoint, middleNodeOfRideRequest, validRidesForMiddleNode2);
        filterValidRidesForEndNodeForTwoRides(rideNodesNearToEndPoint, endNodeOfRideRequest, validRidesForEndNode);
        validRidesForMiddleNode2.retainAll(validRidesForEndNode.keySet());
        return getRidesBasedOnDirection(validRidesForMiddleNode2, validRidesForEndNode, ridesDao);
    }

    private static void filterValidRidesForStartNodeForTwoRides(List<RideNode> rideNodesNearToStartLocation,
                                                                RideRequestNode startNode,
                                                                Set<RideNode> validRidesForStartNode) {
        for (RideNode rideNode : rideNodesNearToStartLocation) {
            double distanceFromStartNode = DistanceFinder.findDistance(startNode.latitude, rideNode.latitude,
                    startNode.longitude, rideNode.longitude);
            if (distanceFromStartNode < MAXIMUM_RIDE_THRESHOLD_KM_FOR_TWO_RIDES && !validRidesForStartNode.contains(rideNode)) {
                validRidesForStartNode.add(rideNode);
            }
        }
    }

    private static void filterValidRidesForEndNodeForTwoRides(List<RideNode> rideNodesNearToEndLocation, RideRequestNode endNode, Map<RideNode, RideNode> validRidesForEndNode) {
        for (RideNode rideNode : rideNodesNearToEndLocation) {
            double distanceFromEndNode = DistanceFinder.findDistance(endNode.latitude, rideNode.latitude,
                    endNode.longitude, rideNode.longitude);
            if (distanceFromEndNode <= MAXIMUM_RIDE_THRESHOLD_KM_FOR_TWO_RIDES && !validRidesForEndNode.containsKey(rideNode)) {
                validRidesForEndNode.put(rideNode, rideNode);
            }
        }
    }

    private static void filterValidRidesForStartNode(List<RideNode> rideNodesNearToStartLocation,
                                                     RideRequestNode startNode,
                                                     Set<RideNode> validRidesForStartNode) {
        for (RideNode rideNode : rideNodesNearToStartLocation) {
            double distanceFromStartNode = DistanceFinder.findDistance(startNode.latitude, rideNode.latitude,
                    startNode.longitude, rideNode.longitude);
            if (distanceFromStartNode < MAXIMUM_RIDE_THRESHOLD_KM && !validRidesForStartNode.contains(rideNode)) {
                validRidesForStartNode.add(rideNode);
            }
        }
    }

    private static void filterValidRidesForEndNode(List<RideNode> rideNodesNearToEndLocation, RideRequestNode endNode, Map<RideNode, RideNode> validRidesForEndNode) {
        for (RideNode rideNode : rideNodesNearToEndLocation) {
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
        for (RideNode rideNode : validRidesForStartNode) {
            RideNode temp = validRidesForEndNode.get(rideNode);
            if (rideNode.sequence <= temp.sequence) {
                Ride currentRide = ride.getRide(rideNode.rideId, ridesDao);
                recommendedRides.add(currentRide);
            }
        }
        return recommendedRides;
    }

    private void convertListOfRidesToListOfListOfRides(List<List<Ride>> resultList, List<Ride> recommendedRides) {
        for (Ride ride: recommendedRides) {
            List<Ride> rides = new ArrayList<>();
            rides.add(ride);
            resultList.add(rides);
        }
    }


}
