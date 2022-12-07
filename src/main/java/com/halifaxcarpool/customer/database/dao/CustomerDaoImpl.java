package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.halifaxcarpool.commons.database.IDatabase;

public class CustomerDaoImpl implements ICustomerDao{

    private final IDatabase database;
    private Connection connection;

    public CustomerDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public void updateCustomerProfile(Customer customer) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            String SQL_STRING = "{CALL update_customer_details(?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setInt(1, customer.getCustomerId());
            stmt.setString(2, customer.getCustomerName());
            stmt.setString(3, customer.getCustomerContact());
            stmt.setString(4, customer.getCustomerEmail());
            stmt.setString(5, customer.getCustomerPassword());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }
}
