package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.driver.database.dao.IRidesDao;

public interface ICustomerObjectFactory {

    IRidesDao getRidesDao();

}
