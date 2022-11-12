package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IViewRides {

    List<Ride> getAllRides();

    List<Ride> getRidesByDriver(int driverId);

}
