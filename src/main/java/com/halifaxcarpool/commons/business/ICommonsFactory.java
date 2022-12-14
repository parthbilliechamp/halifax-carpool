package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.admin.business.statistics.UserStatistics;
import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.business.authentication.encrypter.IPasswordEncrypter;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;

public interface ICommonsFactory {

    IUserAuthentication authenticateUser();
    IGeoCoding getGeoCoding();
    IDirectionPointsProvider getDirectionPointsProvider();
    IPasswordEncrypter getPasswordEncrypter();
    UserStatistics getUserStatistics();

}
