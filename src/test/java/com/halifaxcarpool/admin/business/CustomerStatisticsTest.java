package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserStatistics;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.customer.business.CustomerDaoFactory;
import com.halifaxcarpool.customer.business.CustomerDaoTestFactory;
import com.halifaxcarpool.customer.business.ICustomerDaoFactory;
import org.junit.jupiter.api.Test;

public class CustomerStatisticsTest {

    private static final int numberOfUsers = 19;
    private static final int numberOfRides = 45;
    private static final int numberOfSeats = 65;
    private static final int averageSeats = 4;

    private final IAdminModelFactory adminModelFactory = new AdminModelFactory();

    private final ICustomerDaoFactory customerDaoFactory = new CustomerDaoTestFactory();

    protected IUserDetails userDetails = customerDaoFactory.getCustomerDetailsDao();

    private final IUserStatisticsBuilder userStatisticsBuilder =
            adminModelFactory.getDriverStatisticsBuilder(userDetails);

    @Test
    public void calculateNumberOfUsersTest(){
        userStatisticsBuilder.calculateNumberOfUsers();
        UserStatistics userStatistics = userStatisticsBuilder.getUserStatistics();
        assert userStatistics.getNumberOfUsers() == numberOfUsers;
    }

    @Test
    public void calculateRidesCompletedTest(){
        userStatisticsBuilder.calculateRidesCompleted();
        UserStatistics userStatistics = userStatisticsBuilder.getUserStatistics();
        assert userStatistics.getRidesCompleted() == numberOfRides;
    }

    @Test
    public void calculateNumberOfSeatsTest(){
        userStatisticsBuilder.calculateNumberOfSeats();
        UserStatistics userStatistics = userStatisticsBuilder.getUserStatistics();
        assert userStatistics.getNumberOfSeats() == numberOfSeats;
    }

    @Test
    public void calculateAverageNumberOfSeatsTest(){
        userStatisticsBuilder.calculateAverageNumberOfSeats();
        UserStatistics userStatistics = userStatisticsBuilder.getUserStatistics();
        assert userStatistics.getAverageNumberOfSeats() == averageSeats;
    }

    @Test
    public void calculateAverageRideDistanceTest(){
        userStatisticsBuilder.calculateAverageRideDistance();
        UserStatistics userStatistics = userStatisticsBuilder.getUserStatistics();
        System.out.println(userStatistics.getAverageRideDistance());
        assert userStatistics.getAverageRideDistance() == 1.71;
    }

    @Test
    public void calculateCO2EmissionsTest(){
        userStatisticsBuilder.calculateCO2Emissions();
        UserStatistics userStatistics = userStatisticsBuilder.getUserStatistics();
        System.out.println(userStatistics.getcO2Emissions());
        assert userStatistics.getcO2Emissions() == 0.0;
    }

}
