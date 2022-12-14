package com.halifaxcarpool.commons.business.directions;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.beans.Tuple2;

import java.util.*;

public class DirectionPointsProviderMockImpl implements IDirectionPointsProvider {

    public DirectionPointsProviderMockImpl() {

    }


    @Override
    public List<LatLng> getPointsBetweenSourceAndDestination(String source, String destination) {
        Tuple2 tuple2 = new Tuple2(source, destination);
        String polyLine = DirectionTestSuiteData.locationToPolyLineMap.get(tuple2);
        return DirectionTestSuiteData.polylineToPointsMap.get(polyLine);
    }

    @Override
    public long getDistanceBetweenSourceAndDestination(String source, String destination) {

        return 10;
    }

}
