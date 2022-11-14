package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IRide {

    List<Ride> getAllRides();

    List<Ride> getAllRidesFor(int driverId);

}
