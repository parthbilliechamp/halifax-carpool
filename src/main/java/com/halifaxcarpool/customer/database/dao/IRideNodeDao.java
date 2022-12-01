package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IRideNodeDao {

    void insertRideNodes(List<RideNode> rideNodes);
    List<RideNode> getRideNodes(LatLng latLng);

    int getLatestRideId();

}
