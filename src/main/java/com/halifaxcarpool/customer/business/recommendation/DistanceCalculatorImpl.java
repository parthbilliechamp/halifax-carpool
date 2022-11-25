package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;

public class DistanceCalculatorImpl implements IDistanceCalculator {

    private static final int EARTH_RADIUS_KM = 6371;

    @Override
    public double findDistance(LatLng pointA, LatLng pointB) {

        double longitudeA = convertDegreesToRadian(pointA.longitude);
        double longitudeB = convertDegreesToRadian(pointB.longitude);
        double latitudeA = convertDegreesToRadian(pointA.latitude);
        double latitudeB = convertDegreesToRadian(pointB.latitude);

        double longitudeDifference = longitudeB - longitudeA;
        double latitudeDifference = latitudeB - latitudeA;

        double intermediateResults = Math.pow(Math.sin(latitudeDifference / 2), 2)
                + Math.cos(latitudeA) * Math.cos(latitudeB)
                * Math.pow(Math.sin(longitudeDifference / 2),2);

        double result = 2 * Math.asin(Math.sqrt(intermediateResults));

        return result * EARTH_RADIUS_KM;
    }

    private static double convertDegreesToRadian(double degrees) {
        return Math.toRadians(degrees);
    }

}
