package com.halifaxcarpool.customer.business.recommendation;

import com.halifaxcarpool.customer.business.beans.LatLng;

public interface IGeoCoding {

    LatLng getLatLng(String location);
}
