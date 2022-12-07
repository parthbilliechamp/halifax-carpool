package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerRegistrationDaoImpl implements ICustomerRegistrationDao {

    IDatabase database;
    Connection connection;

    public CustomerRegistrationDaoImpl() {
        database = new DatabaseImpl();
    }

    public void registerCustomer(Customer customer) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            String SQL_STRING = "{CALL insert_customer_details(?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, customer.getCustomerName());
            stmt.setString(2, customer.getCustomerContact());
            stmt.setString(2, customer.getCustomerEmail());
            stmt.setString(2, customer.getCustomerPassword());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }
}
