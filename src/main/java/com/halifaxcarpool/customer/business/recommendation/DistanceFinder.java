package com.halifaxcarpool.customer.business.recommendation;

public class DistanceFinder {

    private static final int EARTH_RADIUS_KM = 6371;

    /**
     * Method to find distance between two geo coordinates with
     */
    public static double findDistance(double latitudeA, double latitudeB, double longitudeA, double longitudeB) {

        longitudeA = convertDegreesToRadian(longitudeA);
        longitudeB = convertDegreesToRadian(longitudeB);
        latitudeA = convertDegreesToRadian(latitudeA);
        latitudeB = convertDegreesToRadian(latitudeB);

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
