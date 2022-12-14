package com.halifaxcarpool.admin.business.statistics;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.business.CommonsFactory;
import com.halifaxcarpool.commons.business.ICommonsFactory;
import com.halifaxcarpool.commons.business.directions.DirectionResultImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionResult;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public abstract class IUserStatisticsBuilder {
    protected IUserDetails userDetails;

    private final UserStatistics userStatistics;

    public IUserStatisticsBuilder(){
        ICommonsFactory commonsFactory = new CommonsFactory();
        this.userStatistics = commonsFactory.getUserStatistics();
    }

    public void calculateNumberOfUsers(){
         userStatistics.setNumberOfUsers(userDetails.getNumberOfUsers());
    }
    public void calculateRidesCompleted(){
        userStatistics.setRidesCompleted(userDetails.getNumberOfRides());
    }
    public void calculateNumberOfSeats(){
        userStatistics.setNumberOfSeats(userDetails.getNumberOfSeats());
    }
    public void calculateAverageNumberOfSeats(){
        userStatistics.setAverageNumberOfSeats(userDetails.getAverageSeats());
    }
    public void calculateAverageRideDistance(){
        List<Double> distances = new ArrayList<>();
        IDirectionResult directionsResult = new DirectionResultImpl();
        Map<Integer, List<String>> rideLocations = userDetails.getRideLocations();
        Iterator<Map.Entry<Integer, List<String>>> itr = rideLocations.entrySet().iterator();
        while (itr.hasNext()){
            Map.Entry<Integer, List<String>> node = itr.next();

            List<String> locations = node.getValue();
            String startLocation = locations.get(0);
            String endLocation = locations.get(1);

            try {
                DirectionsResult directionsResultForRoute = directionsResult.getDirectionsResult(startLocation, endLocation);
                long distance = directionsResultForRoute.routes[0].legs[0].distance.inMeters;
                distances.add((double) distance);
            } catch (IOException | InterruptedException | ApiException e) {
                throw new RuntimeException(e);
            }
        }
        userStatistics.setAverageRideDistance(getAverage(distances));
    }
    public void calculateCO2Emissions(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double emissionsPerCarPerKm = 121.5;
        double emissions = userStatistics.getAverageNumberOfSeats() *
                userStatistics.getAverageRideDistance() * emissionsPerCarPerKm;
        emissions = Double.parseDouble(decimalFormat.format(emissions));
        userStatistics.setCO2Emissions(emissions);
    }

    public UserStatistics getUserStatistics(){
        return this.userStatistics;
    }

    private double getAverage(List<Double> distances){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double sum = 0;
        double average = 0;
        Iterator<Double> itr = distances.iterator();
        while(itr.hasNext()){
            sum += itr.next();
        }
        average = Double.parseDouble(decimalFormat.format((sum/distances.size())/1000));
        return average;
    }
}
