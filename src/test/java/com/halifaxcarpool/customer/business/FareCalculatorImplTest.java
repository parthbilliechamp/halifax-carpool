package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.CommonsTestFactory;
import com.halifaxcarpool.commons.business.ICommonsFactory;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.business.payment.FareCalculatorImpl;
import com.halifaxcarpool.customer.business.payment.IFareCalculator;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class FareCalculatorImplTest{

    private final ICommonsFactory commonsFactory = new CommonsTestFactory();
    @Test
    public void calculateFairTest(){
        IRideRequestsDao rideRequestsDao = new RideRequestsDaoMockImpl();
        IRidesDao ridesDao = new RidesDaoMockImpl();
        IDirectionPointsProvider directionPointsProvider = commonsFactory.getDirectionPointsProvider();
        int rideId = 1;
        int rideRequestId =1;
        IFareCalculator fareCalculator = new FareCalculatorImpl();
        assert (fareCalculator.calculateFair(rideId, rideRequestId, rideRequestsDao, ridesDao, directionPointsProvider) !=0.0);
    }

    @Test
    public void calculateDeductionTest(){
        double originalAmount = 100;
        double discountPercentage = 50.0;
        FareCalculatorImpl fareCalculator = new FareCalculatorImpl(originalAmount, discountPercentage);
        fareCalculator.calculateDeduction();
        assert (Double.compare(fareCalculator.getDeduction(),50) == 0);
    }

    @Test
    public void calculateFinalAmountTest(){
        double originalAmount = 100;
        double discountPercentage = 25.0;
        FareCalculatorImpl fareCalculator = new FareCalculatorImpl(originalAmount, discountPercentage);
        fareCalculator.calculateFinalAmount();
        assert (Double.compare(75.0, fareCalculator.getFinalAmount()) == 0);
    }

}
