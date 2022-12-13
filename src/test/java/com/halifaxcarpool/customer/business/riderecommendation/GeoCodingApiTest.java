package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.CommonsObjectFactoryTest;
import com.halifaxcarpool.commons.business.CommonsFactory;
import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class GeoCodingApiTest {

    CommonsFactory commonsObjectFactory = new CommonsObjectFactoryTest();
    IGeoCoding geoCoding = commonsObjectFactory.getGeoCoding();

    @Disabled("Disabled to save incurring cost caused by calling the REST API ")
    @Test
    void getLatLngTest() {
        LatLng latLng = geoCoding.getLatLng("Sydney");
        assert  -33.8688197 == latLng.getLatitude();
        assert  151.2092955 == latLng.getLongitude();
    }

    @Disabled("Disabled to save incurring cost caused by calling the REST API ")
    @Test
    void getLatLngInvalidLocation() {
        LatLng latLng = geoCoding.getLatLng("abc");
        assert  null == latLng;
    }

}
