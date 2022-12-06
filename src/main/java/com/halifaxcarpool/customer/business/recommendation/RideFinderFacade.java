package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
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
    private static final double MAXIMUM_RIDE_THRESHOLD_KM = 0.5;
    private static final double MAXIMUM_RIDE_THRESHOLD_KM_FOR_TWO_RIDES = 0.2;
    private final IRide ride;

    public RideFinderFacade() {
        ride = new RideImpl();
    }

    public List<Ride> findDirectRouteRides(RideRequest rideRequest, IRideNodeDao rideNodeDao,
                                           IGeoCoding geoCoding, IRidesDao ridesDao) {

        LatLng startLocationPoint = geoCoding.getLatLng(rideRequest.startLocation);
        LatLng endLocationPoint = geoCoding.getLatLng(rideRequest.endLocation);

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

    public List<List<Ride>> findMultipleRouteRides(RideRequest rideRequest, IRideNodeDao rideNodeDao,
                                                   IGeoCoding geoCoding, IRidesDao ridesDao) {
        List<List<Integer>> finalRideRecommendationsList = new ArrayList<>();

        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();

        List<LatLng> rideReqPoints = directionPointsProvider.getPointsBetweenSourceAndDestination(rideRequest.startLocation, rideRequest.endLocation);

        LatLng startPointofRideRequest = rideReqPoints.get(0);

        LatLng endPointofRideRequest = rideReqPoints.get(rideReqPoints.size() - 1);

        for (int i = 1; i < rideReqPoints.size() - 1; i++) {
            LatLng middleSearchPoint = rideReqPoints.get(i);

            List<Ride> ridesForFirstRoute = getRidesForRoute1(rideRequest, rideNodeDao, ridesDao, startPointofRideRequest, middleSearchPoint, rideReqPoints);
            List<Ride> ridesForSecondRoute = getRidesForRoute2(rideRequest, rideNodeDao, ridesDao, middleSearchPoint, endPointofRideRequest, rideReqPoints);

            if (ridesForFirstRoute.size() != 0 && ridesForSecondRoute.size() != 0) {
                for (Ride ride1 : ridesForFirstRoute) {
                    for (Ride ride2 : ridesForSecondRoute) {
                        if (ride1.rideId != ride2.rideId) {
                            List<Integer> rideCombinationsOnEachNode = new ArrayList<>();

                            rideCombinationsOnEachNode.add(ride1.rideId);
                            rideCombinationsOnEachNode.add(ride2.rideId);

                            if (finalRideRecommendationsList.isEmpty()) {
                                finalRideRecommendationsList.add(rideCombinationsOnEachNode);
                            }

                            if (!finalRideRecommendationsList.contains(rideCombinationsOnEachNode)) {
                                finalRideRecommendationsList.add(rideCombinationsOnEachNode);
                            }
                        }
                    }
                }
            }
        }
        List<List<Ride>> rideListToBeRecommended = new ArrayList<>();
        for (List<Integer> rideIdList : finalRideRecommendationsList) {
            List<Ride> ridesListExtracted = new ArrayList<>();
            for (int ride_id : rideIdList) {
                ridesListExtracted.add(ride.getRide(ride_id, ridesDao));
            }
            rideListToBeRecommended.add(ridesListExtracted);
        }

        return rideListToBeRecommended;
    }
    
    private List<Ride> getRidesForRoute1(RideRequest rideRequest, IRideNodeDao rideNodeDao, IRidesDao ridesDao, LatLng startPointofRideRequest, LatLng middleSearchPoint, List<LatLng> rideReqPoints) {

        List<RideNode> rideNodesNearToStartPoint = rideNodeDao.getRideNodes(startPointofRideRequest);
        List<RideNode> rideNodesNearToMiddlePoint = rideNodeDao.getRideNodes(middleSearchPoint);
        RideRequestNode startNodeOfRideRequest =
                new RideRequestNode(startPointofRideRequest.latitude, startPointofRideRequest.longitude,
                        rideRequest.rideRequestId);
        RideRequestNode middleNodeOfRideRequest =
                new RideRequestNode(middleSearchPoint.latitude, middleSearchPoint.longitude,
                        rideRequest.rideRequestId);
        Set<RideNode> validRidesForStartNode = new HashSet<>();
        Map<RideNode, RideNode> validRidesForMiddleNode = new HashMap<>();
        filterValidRidesForStartNodeForTwoRides(rideNodesNearToStartPoint, startNodeOfRideRequest, validRidesForStartNode);
        filterValidRidesForEndNodeForTwoRides(rideNodesNearToMiddlePoint, middleNodeOfRideRequest, validRidesForMiddleNode);
        validRidesForStartNode.retainAll(validRidesForMiddleNode.keySet());
        List<Ride> ridesForFirstRoute = getRidesBasedOnDirection(validRidesForStartNode, validRidesForMiddleNode, ridesDao);
        return ridesForFirstRoute;
    }

    private List<Ride> getRidesForRoute2(RideRequest rideRequest, IRideNodeDao rideNodeDao, IRidesDao ridesDao, LatLng middleSearchPoint, LatLng endPointofRideRequest, List<LatLng> rideReqPoints) {

        List<RideNode> rideNodesNearToMiddlePoint = rideNodeDao.getRideNodes(middleSearchPoint);
        List<RideNode> rideNodesNearToEndPoint = rideNodeDao.getRideNodes(endPointofRideRequest);
        RideRequestNode middleNodeOfRideRequest =
                new RideRequestNode(middleSearchPoint.latitude, middleSearchPoint.longitude,
                        rideRequest.rideRequestId);
        RideRequestNode endNodeOfRideRequest =
                new RideRequestNode(endPointofRideRequest.latitude, endPointofRideRequest.longitude,
                        rideRequest.rideRequestId);
        Set<RideNode> validRidesForMiddleNode2 = new HashSet<>();
        Map<RideNode, RideNode> validRidesForEndNode = new HashMap<>();
        filterValidRidesForStartNodeForTwoRides(rideNodesNearToMiddlePoint, middleNodeOfRideRequest, validRidesForMiddleNode2);
        filterValidRidesForEndNodeForTwoRides(rideNodesNearToEndPoint, endNodeOfRideRequest, validRidesForEndNode);
        validRidesForMiddleNode2.retainAll(validRidesForEndNode.keySet());
        List<Ride> ridesForSecondRoute = getRidesBasedOnDirection(validRidesForMiddleNode2, validRidesForEndNode, ridesDao);
        return ridesForSecondRoute;
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


}
