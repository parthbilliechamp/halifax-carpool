package com.halifaxcarpool.admin.business.statistics;

public class UserAnalysis {
    private final IUserStatisticsBuilder userStatisticsBuilder;

    public UserAnalysis(IUserStatisticsBuilder userStatisticsBuilder){
        this.userStatisticsBuilder = userStatisticsBuilder;
    }

    private void analyze(){
        userStatisticsBuilder.calculateNumberOfUsers();
        userStatisticsBuilder.calculateNumberOfSeats();
        userStatisticsBuilder.calculateRidesCompleted();
        userStatisticsBuilder.calculateAverageNumberOfSeats();
        userStatisticsBuilder.calculateAverageRideDistance();
        userStatisticsBuilder.calculateCO2Emissions();
    }

    public UserStatistics deriveUserStatistics(){
        analyze();
        return this.userStatisticsBuilder.getUserStatistics();
    }
}
