package com.halifaxcarpool.admin.statistics;

public class UserStatistics implements IUserStatistics{

    private int numberOfUsers;
    private int ridesCompleted;
    private int numberOfSeats;
    private int averageNumberOfSeats;
    private float averageRideDistance;
    private double cO2Emissions;

    @Override
    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    @Override
    public void setRidesCompleted(int ridesCompleted) {
        this.ridesCompleted = ridesCompleted;
    }

    @Override
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public void setAverageNumberOfSeats(int averageNumberOfSeats) {
        this.averageNumberOfSeats = averageNumberOfSeats;
    }

    @Override
    public void setAverageRideDistance(float averageRideDistance) {
        this.averageRideDistance = averageRideDistance;
    }

    @Override
    public void setCO2Emissions(double cO2Emissions) {
        this.cO2Emissions = cO2Emissions;
    }
}
