package com.halifaxcarpool.admin.business.statistics;

public class UserStatistics {
    private int numberOfUsers;
    private int ridesCompleted;
    private int numberOfSeats;
    private int averageNumberOfSeats;
    private double averageRideDistance;
    private double cO2Emissions;

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public void setRidesCompleted(int ridesCompleted) {
        this.ridesCompleted = ridesCompleted;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setAverageNumberOfSeats(int averageNumberOfSeats) {
        this.averageNumberOfSeats = averageNumberOfSeats;
    }

    public void setAverageRideDistance(double averageRideDistance) {
        this.averageRideDistance = averageRideDistance;
    }

    public void setCO2Emissions(double cO2Emissions) {
        this.cO2Emissions = cO2Emissions;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public int getRidesCompleted() {
        return ridesCompleted;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public int getAverageNumberOfSeats() {
        return averageNumberOfSeats;
    }

    public double getAverageRideDistance() {
        return averageRideDistance;
    }

    public double getcO2Emissions() {
        return cO2Emissions;
    }
}
