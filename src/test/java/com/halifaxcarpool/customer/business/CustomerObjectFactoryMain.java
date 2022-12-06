package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;

public class CustomerObjectFactoryMain implements ICustomerObjectFactory {

    @Override
    public IRidesDao getRidesDao() {
        return new RidesDaoImpl();
    }

}
