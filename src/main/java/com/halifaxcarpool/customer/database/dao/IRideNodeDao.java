package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.business.beans.RideNode;

import java.util.List;

public interface IRideNodeDao {

    boolean insertRideNodes(List<RideNode> rideNodes);

    List<RideNode> getRideNodes(LatLng latLng);

    int getLatestRideNodeId();

}
