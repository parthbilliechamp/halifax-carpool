package com.halifaxcarpool.customer.business.recommendation;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.halifaxcarpool.customer.business.beans.LatLng;

public class GeoCodingImpl implements IGeoCoding {

//    @Value("${apikey}")
//    private String welcomeMsg;

    @Override
    public LatLng getLatLng(String location) {
        LatLng latLng = null;
        try {
            GeoApiContext context = ApiContextFactory.getGeoApiContextInstance();
            GeocodingResult[] results = GeocodingApi.newRequest(context)
                    .address(location)
                    .await();
            double latitude = results[0].geometry.location.lat;
            double longitude = results[0].geometry.location.lng;
            latLng = new LatLng(latitude, longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }

}
