package com.halifaxcarpool.commons.business.directions;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;

import java.io.IOException;

public interface IDirectionResult {

    DirectionsResult getDirectionsResult(String source, String destination)
            throws IOException, InterruptedException, ApiException;

}
