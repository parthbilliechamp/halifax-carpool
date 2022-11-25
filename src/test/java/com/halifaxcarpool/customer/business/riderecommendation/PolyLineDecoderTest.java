package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.PolylineDecoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
public class PolyLineDecoderTest {

    @Test
    public void decodePolyLineTest() {
        String encodedPolyline =
                "kunoGjvdcK_@oBm@_DYqBg@iBSgAm@cD{@_F}@cFd@I`@OdAe@pCoAlDwAlEeBrAi@W_BWwAfBo@hDsA|HcDdGiCrFyBOaA";
        List<LatLng> decodedLatLngPoints = PolylineDecoder.decodePolyline(encodedPolyline);
        int size = decodedLatLngPoints.size();
        assert 24 == decodedLatLngPoints.size();
        //validating start location and end location
        assert Objects.equals(decodedLatLngPoints.get(0), new LatLng(44.64486, -63.59926));
        assert Objects.equals(decodedLatLngPoints.get(size - 1), new LatLng(44.63759, -63.58745));
    }

}
