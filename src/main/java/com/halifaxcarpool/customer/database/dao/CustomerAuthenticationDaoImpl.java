package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;

import java.sql.Connection;

public class CustomerAuthenticationDaoImpl implements ICustomerAuthenticationDao {

    IDatabase database;
    Connection connection;

    public CustomerAuthenticationDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    @Override
    public boolean authenticate(String username, String password) {
        //#TODO create and write a procedure to authenticate customer
        return true;
    }

}
