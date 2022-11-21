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
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    public void registerCustomer(Customer customer) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `CSCI5308_12_DEVINT`.`customer` (`customer_id`, `customer_name`, `customer_contact`, `customer_email`) VALUES (3, 'Denis NewLop', '(852) 865-623', 'Sh@fdd.com')");
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
