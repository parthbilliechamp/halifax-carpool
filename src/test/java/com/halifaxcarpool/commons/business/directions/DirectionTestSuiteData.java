package com.halifaxcarpool.commons.business.directions;

import com.halifaxcarpool.commons.business.PolylineDecoder;
import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.business.beans.RideNode;

import java.util.*;

/**
 * Feeds data to all the mock implementation used for test cases.
 */
public class DirectionTestSuiteData {

    private static int COUNTER = 0;

    public static Map<Tuple2, String> locationToPolyLineMap = new HashMap<>();

    public static Map<LatLng, String> latLngToPolylineMap = new HashMap<>();
    public static Map<String, List<LatLng>> polylineToPointsMap = new HashMap<>();

    public static Map<String, List<RideNode>> polyLineToRideNodesMap = new HashMap<>();

    public static final Map<String, LatLng> locationToLatLngMap = new HashMap<>();
    
    static {
        populateMockData();
    }
    
    private static void populateMockData() {
        String startLocation = "6056 University Ave, Halifax, NS B3H 1W5";
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        LatLng latLngStartLocation = new LatLng(44.637673535803295, -63.58742788841804);
        LatLng latLngEndLocation = new LatLng(44.64486, -63.59926);

        locationToLatLngMap.put(startLocation, latLngStartLocation);
        locationToLatLngMap.put(endLocation, latLngEndLocation);

        String encodedPolyline =
                "}gmoGplbcK]{Be@PwB~@kCdAuBv@p@|Dx@pFtA`JdAbHzAxJhAvHyDxA_FfBcFxBmCvAmBz@eCjAeBp@wD|AaAd@w@uD";
        latLngToPolylineMap.put(latLngStartLocation, encodedPolyline);
        latLngToPolylineMap.put(latLngEndLocation, encodedPolyline);
        int rideId = 1;
        List<RideNode> rideNodes = prepareRideNodesFrom(encodedPolyline, rideId);
        polyLineToRideNodesMap.put(encodedPolyline, rideNodes);
        Tuple2 tuple2 = new Tuple2(startLocation, endLocation);
        locationToPolyLineMap.put(tuple2, encodedPolyline);
    }

    private static List<RideNode> prepareRideNodesFrom(String encodedPolyLine, int rideId) {
        List<LatLng> points = PolylineDecoder.decodePolyline(encodedPolyLine);
        List<RideNode> rideNodes = new ArrayList<>();
        Iterator<LatLng> iterator = points.iterator();
        while (iterator.hasNext()) {
            LatLng latLng = iterator.next();
            RideNode rideNode = new RideNode(latLng.latitude, latLng.longitude, rideId, ++COUNTER);
            rideNodes.add(rideNode);
        }
        resetCounter();
        return rideNodes;
    }

    private static void resetCounter() {
        COUNTER = 0;
    }
    
}
