package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.geocoding.GeoCodingImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class GeoCodingImplTest {

    @Disabled("Disabled to save incurring cost caused by calling the REST API ")
    @Test
    void test() {
        IGeoCoding geoCoding = new GeoCodingImpl();
        LatLng latLng = geoCoding.getLatLng("Sydney");
        assert  -33.8688197 == latLng.latitude;
        assert  151.2092955 == latLng.longitude;
    }

}
