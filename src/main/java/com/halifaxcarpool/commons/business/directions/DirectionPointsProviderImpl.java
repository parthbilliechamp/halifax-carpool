package com.halifaxcarpool.commons.business.directions;

import com.google.maps.model.DirectionsResult;
import com.halifaxcarpool.commons.business.PolylineDecoder;
import com.halifaxcarpool.commons.business.beans.LatLng;

import java.util.List;

public class DirectionPointsProviderImpl implements IDirectionPointsProvider {

    IDirectionResult directionResult;

    public DirectionPointsProviderImpl() {
        directionResult = new DirectionResultImpl();
    }

    @Override
    public List<LatLng> getPointsBetweenSourceAndDestination(String source,
                                                             String destination) {
        String overviewPolyline = "";
        try {
            DirectionsResult directionsResult = directionResult.getDirectionsResult(source, destination);
            overviewPolyline = directionsResult.routes[0].overviewPolyline.getEncodedPath();
        } catch (Exception e) {
            throw new RuntimeException("Polyline not generated between : " + source + " and " + destination);
        }
        return PolylineDecoder.decodePolyline(overviewPolyline);
    }

    @Override
    public long getDistanceBetweenSourceAndDestination(String source, String destination) {
        try {
            DirectionsResult directionsResult = directionResult.getDirectionsResult(source, destination);
            long distanceInMeters = directionsResult.routes[0].legs[0].distance.inMeters;
            return distanceInMeters / 1000;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Ride distance between : " + source + "and " + destination);
        }
    }

}
