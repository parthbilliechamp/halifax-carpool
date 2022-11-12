package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IViewRidesDao {

    List<Ride> getAllRides();

    List<Ride> getAllRidesForDriver(int driverId);
}
