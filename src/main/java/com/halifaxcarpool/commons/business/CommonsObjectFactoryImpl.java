package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.geocoding.GeoCodingImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;

public class CommonsObjectFactoryImpl implements ICommonsObjectFactory {

    @Override
    public IGeoCoding getGeoCoding() {
        return new GeoCodingImpl();
    }

}
