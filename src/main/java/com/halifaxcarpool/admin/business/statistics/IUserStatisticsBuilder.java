package com.halifaxcarpool.admin.business.statistics;

import com.halifaxcarpool.admin.database.dao.IUserDetails;

import java.util.List;
import java.util.Map;

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
        Map<Integer, List<String>> rideLocations = userDetails.getRideLocations();
        //List<Integer> rideDistances =
        userStatistics.setAverageRideDistance(4.5);
    }
    public void calculateCO2Emissions(){
        userStatistics.setCO2Emissions(422.3);
    }

    public UserStatistics getUserStatistics(){
        return this.userStatistics;
    }
}
