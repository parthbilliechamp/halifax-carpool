package com.halifaxcarpool.commons.business.directions;

import com.google.maps.DirectionsApi;
import com.google.maps.model.DirectionsResult;
import com.halifaxcarpool.commons.business.ApiContextFactory;
import com.halifaxcarpool.commons.business.PolylineDecoder;
import com.halifaxcarpool.commons.business.beans.LatLng;

import java.util.List;

public class DirectionPointsProviderImpl implements IDirectionPointsProvider {

    @Override
    public List<LatLng> getPointsBetweenSourceAndDestination(String source, String destination) {
        String overviewPolyline = "";
        try {
            DirectionsResult directionsResult =
                    DirectionsApi.getDirections(ApiContextFactory.getGeoApiContextInstance(), source, destination)
                            .await();
            overviewPolyline = directionsResult.routes[0].overviewPolyline.getEncodedPath();
            System.out.println(overviewPolyline);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PolylineDecoder.decodePolyline(overviewPolyline);
    }

}
