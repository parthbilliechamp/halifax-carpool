package com.halifaxcarpool.commons.business.geocoding;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.halifaxcarpool.commons.business.ApiContextFactory;
import com.halifaxcarpool.commons.business.beans.LatLng;

public class GeoCodingImpl implements IGeoCoding {

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
