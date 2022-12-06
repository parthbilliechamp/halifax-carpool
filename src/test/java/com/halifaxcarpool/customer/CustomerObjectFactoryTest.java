package com.halifaxcarpool.customer;

import com.halifaxcarpool.customer.business.ICustomerObjectFactory;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;

public class CustomerObjectFactoryTest implements ICustomerObjectFactory {

    @Override
    public IRidesDao getRidesDao() {
        return new RidesDaoMockImpl();
    }
}
