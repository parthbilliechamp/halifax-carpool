package com.halifaxcarpool.customer.business.recommendation;

public abstract class RideFinderBaseDecorator extends BaseRideFinder {

    BaseRideFinder rideFinder;

    public RideFinderBaseDecorator(BaseRideFinder rideFinder) {
        this.rideFinder = rideFinder;
    }

}
