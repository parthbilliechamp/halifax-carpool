package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;

public interface ICommonsObjectFactory {

    IGeoCoding getGeoCoding();
}
