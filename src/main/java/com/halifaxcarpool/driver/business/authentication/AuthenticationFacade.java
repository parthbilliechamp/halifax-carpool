package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;

public class AuthenticationFacade {

    public Driver authenticate(String userName, String password) {

        IDriverLogin driverLogin = new DriverLoginImpl();
        IDriverAuthentication driverAuthentication = new DriverAuthenticationImpl();
        return driverLogin.login(userName, password, driverAuthentication);
    }

}
