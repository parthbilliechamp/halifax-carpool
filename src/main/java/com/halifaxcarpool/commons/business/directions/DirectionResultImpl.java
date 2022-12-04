package com.halifaxcarpool.commons.business.directions;

import com.google.maps.DirectionsApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.halifaxcarpool.commons.business.ApiContextFactory;

import java.io.IOException;

public class DirectionResultImpl implements IDirectionResult {
    @Override
    public DirectionsResult getDirectionsResult(String source, String destination)
            throws IOException, InterruptedException, ApiException {
        return DirectionsApi
                .getDirections(ApiContextFactory.getGeoApiContextInstance(), source, destination)
                    .await();
    }
}
