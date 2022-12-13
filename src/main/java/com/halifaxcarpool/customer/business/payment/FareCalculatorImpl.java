package com.halifaxcarpool.customer.business.payment;

import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public class FareCalculatorImpl implements IFareCalculator{
    public static final int THRESHOLD_REQUESTS = 5;
    public static final double BASE_MULTIPLICATION_FACTOR = 10;
    public static final double  INFLATION_MULTIPLICATION_FACTOR= 20;


    @Override
    public double calculateFair(int rideId, IRideRequestsDao rideRequestsDao, IRidesDao ridesDao) {

        int requestCount = rideRequestsDao.getRideRequestCount(rideId);
        int numSeats = ridesDao.getRide(rideId).getSeatsOffered();
        System.out.println("no of seats"+numSeats);
        int distance = 10; //from Ride(Driver's) get start and end location and get distance.
        int numOfSeatsOffered = ridesDao.getRide(rideId).getSeatsOffered();
        double fare = 0;
        if(requestCount > THRESHOLD_REQUESTS){
            fare = (INFLATION_MULTIPLICATION_FACTOR * distance)/ numOfSeatsOffered;
        }
        else{
            fare = (BASE_MULTIPLICATION_FACTOR * distance)/ numOfSeatsOffered;
        }

        return fare;
    }
}
