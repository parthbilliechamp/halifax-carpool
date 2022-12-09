package com.halifaxcarpool.admin.business.statistics;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.business.directions.DirectionResultImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionResult;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public abstract class IUserStatisticsBuilder {
    protected IUserDetails userDetails;

    private UserStatistics userStatistics;

    public IUserStatisticsBuilder(){
        this.userStatistics = new UserStatistics();
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
        //List<Integer> rideDistances =
        Iterator<Map.Entry<Integer, List<String>>> itr = rideLocations.entrySet().iterator();
        while (itr.hasNext()){
            Map.Entry<Integer, List<String>> node = itr.next();

            List<String> locations = node.getValue();
            String start_loc = locations.get(0);
            String end_loc = locations.get(1);

            try {
                DirectionsResult directionsResultForRoute = directionsResult.getDirectionsResult(start_loc, end_loc);
                Long distance = directionsResultForRoute.routes[0].legs[0].distance.inMeters;
                distances.add(distance.doubleValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
        userStatistics.setAverageRideDistance(getAverage(distances));
    }
    public void calculateCO2Emissions(){
        DecimalFormat df = new DecimalFormat("0.00");
        final double emissionsPerCarPerKm = 121.5;
        double emissions = userStatistics.getAverageNumberOfSeats() *
                userStatistics.getAverageRideDistance() * emissionsPerCarPerKm;
        emissions = Double.valueOf(df.format(emissions));
        userStatistics.setCO2Emissions(emissions);
    }

    public UserStatistics getUserStatistics(){
        return this.userStatistics;
    }

    private double getAverage(List<Double> distances){
        DecimalFormat df = new DecimalFormat("0.00");
        double sum = 0;
        double average = 0;
        Iterator<Double> itr = distances.iterator();
        while(itr.hasNext()){
            sum += itr.next();
        }
        average = Double.valueOf(df.format((sum/distances.size())/1000));
        return average;
    }
}
