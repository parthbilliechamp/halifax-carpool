package com.halifaxcarpool.driver.business;

import com.google.maps.DirectionsApi;
import com.google.maps.model.DirectionsResult;
import com.halifaxcarpool.commons.business.ApiContextFactory;

public class Directions {

    public static void main(String[] args) {
        String origin = "McDonald's, 6324 Quinpool Rd, Halifax, NS B3L 1A5";
        String destination = "Goldberg Computer Science Building, 6056 University Ave, Halifax, NS B3H 1W5";
        try {
            DirectionsResult directionsResult =
                    DirectionsApi.getDirections(ApiContextFactory.getGeoApiContextInstance(), origin, destination)
                            .await();
            String overviewPolyline = directionsResult.routes[0].overviewPolyline.getEncodedPath();
            System.out.println(directionsResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
