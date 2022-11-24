package com.halifaxcarpool.customer.business.recommendation;

import com.google.maps.GeoApiContext;

public class ApiContextFactory {

    private static final String API_KEY = "AIzaSyA8ImVWuK61HyvAM3XeRmLj2bBXUA7cjoc";

    private static GeoApiContext context = null;

    public static GeoApiContext getGeoApiContextInstance() {
        if (null == context) {
            context = new GeoApiContext.Builder()
                    .apiKey(API_KEY)
                    .build();
        }
        return context;
    }

}
