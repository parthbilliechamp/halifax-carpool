package com.halifaxcarpool.commons.business.directions;

import com.google.maps.DirectionsApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.halifaxcarpool.commons.business.ApiContextFactory;
import com.halifaxcarpool.commons.business.beans.Tuple2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DirectionResultImpl implements IDirectionResult {

    Map<Tuple2, DirectionsResult> cacheManager = new HashMap<>();
    @Override
    public DirectionsResult getDirectionsResult(String source, String destination)
            throws IOException, InterruptedException, ApiException {
        Tuple2 lookupKey = new Tuple2(source, destination);
        if (cacheManager.containsKey(lookupKey)) {
            return cacheManager.get(lookupKey);
        } else {
            DirectionsResult result = DirectionsApi.
                    getDirections(ApiContextFactory.getGeoApiContextInstance(), source, destination).
                    await();
            cacheManager.put(lookupKey, result);
            return result;
        }
    }

}
