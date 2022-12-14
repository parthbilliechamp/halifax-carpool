package com.halifaxcarpool.customer.business.payment;

import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public class FareCalculatorImpl implements IFareCalculator{
    public static final int THRESHOLD_REQUESTS = 5;
    public static final double BASE_MULTIPLICATION_FACTOR = 10;
    public static final double  INFLATION_MULTIPLICATION_FACTOR= 20;
    private double originalAmount;
    private double deduction;
    private double finalAmount;
    private double discountPercentage;

    public FareCalculatorImpl(double originalAmount, double discountPercentage){
        this.originalAmount = originalAmount;
        this.discountPercentage = discountPercentage;
    }

    public FareCalculatorImpl() {

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
    public double calculateFair(int rideId, int rideRequestId, IRideRequestsDao rideRequestsDao,
                                IRidesDao ridesDao, IDirectionPointsProvider directionPointsProvider) {

        int requestCount = rideRequestsDao.getRideRequestCount(rideId);
        RideRequest rideRequest = rideRequestsDao.getRideRequest(rideRequestId);
        int distance = (int)(directionPointsProvider.
                getDistanceBetweenSourceAndDestination(rideRequest.getStartLocation(),
                        rideRequest.getEndLocation()));
        int numOfSeatsOffered = ridesDao.getRide(rideId).getSeatsOffered();
        double fare;

        if(requestCount > THRESHOLD_REQUESTS){
            fare = (INFLATION_MULTIPLICATION_FACTOR * distance)/ numOfSeatsOffered;
        }
        else{
            fare = (BASE_MULTIPLICATION_FACTOR * distance)/ numOfSeatsOffered;
        }
        fare = Math.round(fare);

        return fare;
    }

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
}
