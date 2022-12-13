package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.payment.FareCalculatorImpl;
import com.halifaxcarpool.customer.business.payment.IFareCalculator;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class FareCalculatorImplTest{
    @Test
    public void calculateFairTest(){
        IRideRequestsDao rideRequestsDao = new RideRequestsDaoMockImpl();
        IRidesDao ridesDao = new RidesDaoMockImpl();
        int rideId = 1;
        IFareCalculator fareCalculator = new FareCalculatorImpl();
        assert (fareCalculator.calculateFair(rideId, rideRequestsDao, ridesDao) ==0.0);
    }

    @Test
    public void calculateDeductionTest(){
        double originalAmount = 100;
        double discountPercentage = 50.0;
        IFareCalculator fareCalculator = new FareCalculatorImpl(originalAmount, discountPercentage);
        fareCalculator.calculateDeduction();
        assert (Double.compare(((FareCalculatorImpl)fareCalculator).getDeduction(),50) == 0);
    }

    @Test
    public void calculateFinalAmountTest(){
        double originalAmount = 100;
        double discountPercentage = 25.0;
        FareCalculatorImpl fareCalculator = new FareCalculatorImpl(originalAmount, discountPercentage);
        fareCalculator.calculateFinalAmount();
        assert (Double.compare(75.0, ((FareCalculatorImpl)fareCalculator).getFinalAmount()) == 0);
    }

}
