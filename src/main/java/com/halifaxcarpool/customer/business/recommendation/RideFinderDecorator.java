package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public abstract class RideFinderDecorator extends RideFinder {

    RideFinder rideFinder;

    RideFinderDecorator(RideFinder rideFinder) {
        this.rideFinder = rideFinder;
    }

}
