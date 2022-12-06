package com.halifaxcarpool.admin.statistics;

public interface IStatisticsGenerator {
    void calculateNumberOfUsers();
    void calculateRidesCompleted();
    void calculateNumberOfSeats();
    void calculateAverageNumberOfSeats();
    void calculateAverageRideDistance();
    void calculateCO2Emissions();
}
