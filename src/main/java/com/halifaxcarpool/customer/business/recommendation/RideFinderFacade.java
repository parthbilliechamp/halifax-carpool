package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.beans.RideRequestNode;
import com.halifaxcarpool.customer.business.beans.RouteFinderParameter;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.IDriverModelFactory;
import com.halifaxcarpool.driver.business.DriverModelFactory;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

import java.util.*;

public class RideFinderFacade {

    private static final double MAXIMUM_RIDE_THRESHOLD_KM = 0.2;

    private final IRide ride;

    public RideFinderFacade() {
        IDriverModelFactory driverModelFactory = new DriverModelFactory();
        ride = driverModelFactory.getDriverRide();
    }

    public List<List<Ride>> findDirectRouteRidesInvoker(RideRequest rideRequest,
                                                        IRideNodeDao rideNodeDao,
                                                        IGeoCoding geoCoding,
                                                        IRidesDao ridesDao) {

        LatLng startLocationPoint = geoCoding.getLatLng(rideRequest.getStartLocation());
        LatLng endLocationPoint = geoCoding.getLatLng(rideRequest.getEndLocation());

        if (null == startLocationPoint || null == endLocationPoint) {
            throw new RuntimeException("Error finding coordinates of the ride request : " +
                    rideRequest.getRideRequestId());
        }
        RouteFinderParameter routeFinderParameter =
                new RouteFinderParameter(rideRequest, rideNodeDao, ridesDao, startLocationPoint, endLocationPoint);
        List<Ride> recommendedRides = findDirectRouteRides(routeFinderParameter);
        List<List<Ride>> recommendedRidesWrapper = new ArrayList<>();
        convertListOfRidesToListOfListOfRides(recommendedRidesWrapper, recommendedRides);
        return recommendedRidesWrapper;
    }

    public List<List<Ride>> findMultipleRouteRides(RideRequest rideRequest,
                                                   IDirectionPointsProvider directionPointsProvider,
                                                   IRideNodeDao rideNodeDao,
                                                   IRidesDao ridesDao) {
        List<LatLng> rideRequestNodes = directionPointsProvider.
                getPointsBetweenSourceAndDestination(rideRequest.getStartLocation(), rideRequest.getEndLocation());

        if (null == rideRequestNodes || rideRequestNodes.isEmpty()) {
            return new ArrayList<>();
        }

        LatLng rideRequestStartNode = rideRequestNodes.get(0);
        LatLng endPointOfRideRequest = rideRequestNodes.get(rideRequestNodes.size() - 1);
        Map<RideLookupKey, List<Ride>> ridesCache = new HashMap<>();

        for (int i = 1; i < rideRequestNodes.size() - 1 && ridesCache.size() <= 3; i += 3) {
            LatLng intermediateNode = rideRequestNodes.get(i);
            RouteFinderParameter firstRouterFinderParameter = new RouteFinderParameter(rideRequest, rideNodeDao,
                    ridesDao, rideRequestStartNode, intermediateNode);
            List<Ride> ridesForFirstRoute =
                    findDirectRouteRides(firstRouterFinderParameter);
            RouteFinderParameter secondRouteFinderParameter = new RouteFinderParameter(rideRequest, rideNodeDao,
                    ridesDao, intermediateNode, endPointOfRideRequest);
            List<Ride> ridesForSecondRoute =
                    findDirectRouteRides(secondRouteFinderParameter);

            if (0 != ridesForFirstRoute.size() && 0 != ridesForSecondRoute.size()) {
                for (Ride ride1 : ridesForFirstRoute) {
                    for (Ride ride2 : ridesForSecondRoute) {
                        if (ride1.getRideId() == ride2.getRideId()) {
                            continue;
                        }
                        RideLookupKey rideLookupKey = new RideLookupKey(ride1.getRideId(), ride2.getRideId());
                        List<Ride> combinedRides = new ArrayList<>();
                        combinedRides.add(ride1);
                        combinedRides.add(ride2);
                        ridesCache.put(rideLookupKey, combinedRides);
                    }
                }
            }
        }
        return new ArrayList<>(ridesCache.values());
    }

    private List<Ride> findDirectRouteRides(RouteFinderParameter routeFinderParameter) {
        IRideNodeDao rideNodeDao = routeFinderParameter.getRideNodeDao();
        LatLng startLocationPoint = routeFinderParameter.getStartLocationPoint();
        LatLng endLocationPoint = routeFinderParameter.getEndLocationPoint();
        RideRequest rideRequest = routeFinderParameter.getRideRequest();
        IRidesDao ridesDao = routeFinderParameter.getRidesDao();

        List<RideNode> rideNodesNearToStartLocation = rideNodeDao.getRideNodes(startLocationPoint);
        List<RideNode> rideNodesNearToEndLocation = rideNodeDao.getRideNodes(endLocationPoint);

        RideRequestNode startNode =
                new RideRequestNode(startLocationPoint.getLatitude(), startLocationPoint.getLongitude(),
                        rideRequest.getRideRequestId());
        RideRequestNode endNode =
                new RideRequestNode(endLocationPoint.getLatitude(), endLocationPoint.getLongitude(),
                        rideRequest.getRideRequestId());

        Set<RideNode> validRidesForStartNode =
                filterValidRidesForStartNode(rideNodesNearToStartLocation, startNode);
        Map<RideNode, RideNode> validRidesForEndNodeIdenticalMap =
                filterValidRidesForEndNode(rideNodesNearToEndLocation, endNode);

        validRidesForStartNode.retainAll(validRidesForEndNodeIdenticalMap.keySet());
        return getRidesBasedOnDirection(validRidesForStartNode, validRidesForEndNodeIdenticalMap, ridesDao);
    }

    private static Set<RideNode> filterValidRidesForStartNode(List<RideNode> rideNodesNearToStartLocation,
                                                              RideRequestNode startNode) {
        Set<RideNode> validRidesForStartNode = new HashSet<>();
        for (RideNode rideNode : rideNodesNearToStartLocation) {
            LatLng startLocationLatLng = new LatLng(startNode.getLatitude(), startNode.getLongitude());
            LatLng intermediateLocationLatLng = new LatLng(rideNode.getLatitude(), rideNode.getLongitude());
            double distanceFromStartNode = DistanceFinder.findDistance(startLocationLatLng, intermediateLocationLatLng);
            if (distanceFromStartNode < MAXIMUM_RIDE_THRESHOLD_KM && !validRidesForStartNode.contains(rideNode)) {
                validRidesForStartNode.add(rideNode);
            }
        }
        return validRidesForStartNode;
    }

    private static Map<RideNode, RideNode> filterValidRidesForEndNode(List<RideNode> rideNodesNearToEndLocation,
                                                                      RideRequestNode endNode) {
        Map<RideNode, RideNode> validRidesForEndNode = new HashMap<>();
        for (RideNode rideNode : rideNodesNearToEndLocation) {
            LatLng endLocationLatLng = new LatLng(endNode.getLatitude(), endNode.getLongitude());
            LatLng intermediateLocationLatLng = new LatLng(rideNode.getLatitude(), rideNode.getLongitude());
            double distanceFromEndNode = DistanceFinder.findDistance(endLocationLatLng, intermediateLocationLatLng);
            if (distanceFromEndNode <= MAXIMUM_RIDE_THRESHOLD_KM && !validRidesForEndNode.containsKey(rideNode)) {
                validRidesForEndNode.put(rideNode, rideNode);
            }
        }
        return validRidesForEndNode;
    }

    private List<Ride> getRidesBasedOnDirection(Set<RideNode> validRidesForStartNode,
                                                Map<RideNode, RideNode> validRidesForEndNode,
                                                IRidesDao ridesDao) {
        List<Ride> recommendedRides = new ArrayList<>();
        for (RideNode rideNode : validRidesForStartNode) {
            RideNode temp = validRidesForEndNode.get(rideNode);
            if (rideNode.getSequence() <= temp.getSequence()) {
                Ride currentRide = ride.getRide(rideNode.getRideId(), ridesDao);
                recommendedRides.add(currentRide);
            }
        }
        return recommendedRides;
    }

    private void convertListOfRidesToListOfListOfRides(List<List<Ride>> resultList,
                                                       List<Ride> recommendedRides) {
        for (Ride ride : recommendedRides) {
            List<Ride> rides = new ArrayList<>();
            rides.add(ride);
            resultList.add(rides);
        }
    }

}
