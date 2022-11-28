package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;

public interface IDriverAuthentication {

    Driver authenticate(String userName, String password);

}
