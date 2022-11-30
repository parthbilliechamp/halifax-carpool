package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.List;

public interface IRideNode {

    void insertRideNodes(Ride ride, IRideNodeDao rideNodeDao, IDirectionPointsProvider directionPointsProvider);

    List<RideNode> getRideNodes(LatLng latLng, IRideNodeDao rideNodeDao);
}
