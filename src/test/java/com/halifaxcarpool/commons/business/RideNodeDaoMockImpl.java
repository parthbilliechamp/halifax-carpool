package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;

import java.util.List;

import static com.halifaxcarpool.commons.business.directions.DirectionTestSuiteData.latLngToPolylineMap;
import static com.halifaxcarpool.commons.business.directions.DirectionTestSuiteData.polyLineToRideNodesMap;

public class RideNodeDaoMockImpl implements IRideNodeDao {
    @Override
    public void insertRideNodes(List<RideNode> rideNodes) {

    }

    @Override
    public List<RideNode> getRideNodes(LatLng latLng) {
        String polyline = latLngToPolylineMap.get(latLng);
        return polyLineToRideNodesMap.get(polyline);
    }

}
