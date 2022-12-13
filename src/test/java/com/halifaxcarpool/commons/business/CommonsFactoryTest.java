package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.business.authentication.UserAuthenticationImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderMockImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.commons.business.geocoding.GeoCodingMockImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;

public class CommonsFactoryTest implements ICommonsFactory {

    @Override
    public IUserAuthentication authenticateUser() {
        return new UserAuthenticationImpl();
    }

    @Override
    public IGeoCoding getGeoCoding() {
        return new GeoCodingMockImpl();
    }

    @Override
    public IDirectionPointsProvider getDirectionPointsProvider() {
        return new DirectionPointsProviderMockImpl();
    }


}
