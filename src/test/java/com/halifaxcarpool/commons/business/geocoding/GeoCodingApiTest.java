package com.halifaxcarpool.commons.business.geocoding;

import com.halifaxcarpool.commons.business.CommonsTestFactory;
import com.halifaxcarpool.commons.business.ICommonsFactory;
import com.halifaxcarpool.commons.business.beans.LatLng;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
public class GeoCodingApiTest {

    ICommonsFactory commonsObjectFactory = new CommonsTestFactory();
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
