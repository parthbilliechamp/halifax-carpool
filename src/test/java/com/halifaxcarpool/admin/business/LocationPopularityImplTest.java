package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.popular.ILocationPopularity;
import com.halifaxcarpool.admin.business.popular.LocationPopularityImpl;
import com.halifaxcarpool.admin.database.dao.ILocationPopularityDao;
import com.halifaxcarpool.admin.database.dao.LocationPopularityDaoMock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class LocationPopularityImplTest {

    private IAdminModelFactory adminModelFactory = new AdminModelFactory();
    private IAdminDaoFactory adminDaoFactory = new AdminDaoTestFactory();
    private ILocationPopularityDao locationPopularityDao = adminDaoFactory.getLocationPopularityDao();
    private ILocationPopularity locationPopularity = adminModelFactory.getLocationPopularity(locationPopularityDao);

    @Test
    public void getPopularPickUpLocationsTest(){
        Map<Integer, List<String>> locations = locationPopularity.getPopularPickUpLocations();
        assert locations.containsKey(3);
        assertFalse(locations.containsKey(4));
    }

}
