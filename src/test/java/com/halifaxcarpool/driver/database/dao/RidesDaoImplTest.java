package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Ride;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RidesDaoImplTest {

    @Test
    void viewRidesTest() {
        int driverId = 1;
        IRidesDao ridesDao = new RidesDaoMockImpl();
        List<Ride> rides = ridesDao.getRides(driverId);
        assert 2 == rides.size();
        for (Ride ride: rides) {
            assert driverId == ride.driverId;
        }
    }

}
