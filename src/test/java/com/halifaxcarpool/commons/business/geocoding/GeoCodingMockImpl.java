package com.halifaxcarpool.commons.business.geocoding;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.directions.DirectionTestSuiteData;
public class GeoCodingMockImpl implements IGeoCoding {

    @Override
    public LatLng getLatLng(String location) {
        return DirectionTestSuiteData.locationToLatLngMap.get(location);
    }

}
