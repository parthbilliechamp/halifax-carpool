package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerRegistrationDaoImpl implements ICustomerRegistrationDao {

    IDatabase database;
    Connection connection;

    public CustomerRegistrationDaoImpl() {

    }

    public void registerCustomer(Customer customer) {
        try {
            database = new DatabaseImpl();
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL insert_customer_details('"+ customer.getCustomerName() + "', '" + customer.getCustomerContact() + "', '" + customer.getCustomerEmail() + "', '" + customer.getCustomerPassword() + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
