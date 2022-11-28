package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.driver.business.beans.Driver;

public interface IDriverLogin {

    Driver login(String userName, String password, IDriverAuthentication driverAuthentication);

}
