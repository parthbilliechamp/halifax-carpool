package com.halifaxcarpool.admin.business.statistics;

public interface IUserStatistics {
    //TODO instead of set use something like populateNumberOfUsers, set looks like simple setters
    void setNumberOfUsers(int numberOfUsers);
    void setRidesCompleted(int ridesCompleted);
    void setNumberOfSeats(int numberOfSeats);
    void setAverageNumberOfSeats(int averageNumberOfSeats);
    void setAverageRideDistance(double averageRideDistance);
    void setCO2Emissions(double cO2Emissions);
}
