package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.LatLng;
import com.halifaxcarpool.customer.business.beans.RideNode;

import java.util.List;

public interface IRideNodeDao {

    List<RideNode> getRideNodes(LatLng latLng);

}
