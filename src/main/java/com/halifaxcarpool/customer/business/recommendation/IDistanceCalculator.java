package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.LatLng;

/**
 * finds the distance between two get coordinates
 */
public interface IDistanceCalculator {

    double findDistance(LatLng pointA, LatLng pointB);

}
