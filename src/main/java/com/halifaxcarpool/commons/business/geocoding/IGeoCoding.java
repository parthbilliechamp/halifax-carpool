package com.halifaxcarpool.commons.business.geocoding;

import com.halifaxcarpool.commons.business.beans.LatLng;

public interface IGeoCoding {

    LatLng getLatLng(String location);

}
