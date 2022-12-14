package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.admin.business.statistics.UserStatistics;
import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.business.authentication.UserAuthenticationImpl;
import com.halifaxcarpool.commons.business.authentication.encrypter.IPasswordEncrypter;
import com.halifaxcarpool.commons.business.authentication.encrypter.PasswordEncrypterImpl;
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

    @Override
    public IPasswordEncrypter getPasswordEncrypter() {
        return new PasswordEncrypterImpl();
    }

    @Override
    public UserStatistics getUserStatistics() {
        return new UserStatistics();
    }

}
