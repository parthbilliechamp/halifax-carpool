package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.beans.User;

public interface IDriverModelFactory {

    User getDriver();

    IRide getDriverRide();

    IRideNode getRideNode();

    IRideToRequestMapper getRideToRequestMapper();

}
