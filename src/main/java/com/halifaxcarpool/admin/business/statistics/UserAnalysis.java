package com.halifaxcarpool.admin.business.statistics;

public class UserAnalysis {
    private IUserStatisticsBuilder userStatisticsBuilder;

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

    public UserStatistics deriveDriverStatistics(){
        analyze();
        return this.userStatisticsBuilder.getUserStatistics();
    }
}
