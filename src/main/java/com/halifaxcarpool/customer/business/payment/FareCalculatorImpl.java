package com.halifaxcarpool.customer.business.payment;

import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public class FareCalculatorImpl implements IFareCalculator{
    public static final int THRESHOLD_REQUESTS = 5;
    public static final double BASE_MULTIPLICATION_FACTOR = 10;
    public static final double  INFLATION_MULTIPLICATION_FACTOR= 20;
    public double originalAmount;
    public double deduction;
    public double finalAmount;

    public double discountPercentage;

    public double getFinalAmount(){
        return this.finalAmount;
    }
    public double getDeduction(){
        return this.deduction;
    }
    public double getOriginalAmount(){
        return this.originalAmount;
    }
    public double getDiscountPercentage(){
        return this.discountPercentage;
    }
    public FareCalculatorImpl(double originalAmount, double discountPercentage){
        this.originalAmount = originalAmount;
        this.discountPercentage = discountPercentage;
    }
    public FareCalculatorImpl(){

    }
    public double calculateFinalAmount(){
        calculateDeduction();
        this.finalAmount = this.originalAmount - this.deduction;
        return this.finalAmount;
    }

    public void calculateDeduction(){
        this.deduction = (this.originalAmount*this.discountPercentage)/100;
    }


    @Override
    public double calculateFair(int rideId, IRideRequestsDao rideRequestsDao, IRidesDao ridesDao) {

        int requestCount = rideRequestsDao.getRideRequestCount(rideId);
        int numSeats = ridesDao.getRide(rideId).getSeatsOffered();
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
