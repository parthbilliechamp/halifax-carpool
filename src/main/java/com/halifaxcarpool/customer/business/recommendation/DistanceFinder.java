package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;

public class DistanceFinder {

    private static final int EARTH_RADIUS_KM = 6371;

    /**
     * Method to find distance between two geo coordinates
     */
    public static double findDistance(LatLng latLngStart, LatLng latLngEnd) {

        double longitudeA = convertDegreesToRadian(latLngStart.getLongitude());
        double longitudeB = convertDegreesToRadian(latLngEnd.getLongitude());
        double latitudeA = convertDegreesToRadian(latLngStart.getLatitude());
        double latitudeB = convertDegreesToRadian(latLngEnd.getLatitude());

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
