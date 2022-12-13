package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.business.authentication.UserAuthenticationImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.commons.business.geocoding.GeoCodingImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;

public class CommonsFactory implements ICommonsFactory {

    @Override
    public IUserAuthentication authenticateUser() {
        return new UserAuthenticationImpl();
    }

    @Override
    public IGeoCoding getGeoCoding() {
        return new GeoCodingImpl();
    }

    @Override
    public IDirectionPointsProvider getDirectionPointsProvider() {
        return new DirectionPointsProviderImpl();
    }

}
