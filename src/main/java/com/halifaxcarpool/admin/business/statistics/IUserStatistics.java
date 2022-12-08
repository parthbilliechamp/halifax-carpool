package com.halifaxcarpool.admin.business.statistics;

public interface IUserStatistics {
    void setNumberOfUsers(int numberOfUsers);
    void setRidesCompleted(int ridesCompleted);
    void setNumberOfSeats(int numberOfSeats);
    void setAverageNumberOfSeats(int averageNumberOfSeats);
    void setAverageRideDistance(double averageRideDistance);
    void setCO2Emissions(double cO2Emissions);
}
