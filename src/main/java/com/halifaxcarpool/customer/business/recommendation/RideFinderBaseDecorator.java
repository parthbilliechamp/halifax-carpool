package com.halifaxcarpool.customer.business.recommendation;

public abstract class RideFinderBaseDecorator extends RideFinder {

    RideFinder rideFinder;

    public RideFinderBaseDecorator(RideFinder rideFinder) {
        this.rideFinder = rideFinder;
    }

}
