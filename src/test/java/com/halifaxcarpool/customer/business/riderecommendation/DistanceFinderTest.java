package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.business.recommendation.DistanceFinder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DistanceFinderTest {

    @Test
    public void findDistanceTest() {
        LatLng startLocation = new LatLng(43.01, -63.01);
        LatLng endLocation = new LatLng(43.11, -63.05);
        assert 11.584639898232995 == DistanceFinder.findDistance(startLocation, endLocation);
    }

}
