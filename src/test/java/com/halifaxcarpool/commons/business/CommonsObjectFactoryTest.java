package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.geocoding.GeoCodingMockImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;

public class CommonsObjectFactoryTest implements ICommonsObjectFactory {

    @Override
    public IGeoCoding getGeoCoding() {
        return new GeoCodingMockImpl();
    }

}
