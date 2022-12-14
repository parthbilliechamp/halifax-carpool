package com.halifaxcarpool.commons.business.directions;

import com.halifaxcarpool.commons.business.beans.LatLng;

import java.util.List;

public interface IDirectionPointsProvider {

    List<LatLng> getPointsBetweenSourceAndDestination(String source, String destination);
    long getDistanceBetweenSourceAndDestination(String source, String destination);

}
