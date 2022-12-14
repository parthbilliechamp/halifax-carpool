package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.beans.Ride;

public class DriverModelFactory implements IDriverModelFactory {

    @Override
    public User getDriver() {
        return new Driver();
    }

    @Override
    public IRide getDriverRide() {
        return new Ride();
    }

    @Override
    public IRideNode getRideNode() {
        return new RideNodeImpl();
    }

    @Override
    public IRideToRequestMapper getRideToRequestMapper() {
        return new RideToRequestMapperImpl();
    }
}
