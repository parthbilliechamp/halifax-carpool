package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;

public interface ICommonsObjectFactory {

    IUserAuthentication authenticateUser();
    IGeoCoding getGeoCoding();
}
