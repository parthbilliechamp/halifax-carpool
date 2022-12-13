package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerAuthenticationDaoImpl implements IUserAuthenticationDao {

    IDatabase database;
    Connection connection;

    public CustomerAuthenticationDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public Customer authenticate(String username, String password) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL login_customer('" + username + "', '" + password + "')");
            return buildCustomerFrom(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

    private static Customer buildCustomerFrom(ResultSet resultSet) throws SQLException {

        Customer customer = null;
        while (resultSet.next()) {
            String customerIdLabel = "customer_id";
            String customerNameLiteral = "customer_name";
            String customerContact1 = "customer_contact";
            String customerEmailLiteral = "customer_email";
            String customerPasswordLiteral = "customer_password";

            int customerId = Integer.parseInt(resultSet.getString(customerIdLabel));
            String customerName = resultSet.getString(customerNameLiteral);
            String customerContact = resultSet.getString(customerContact1);
            String customerEmail = resultSet.getString(customerEmailLiteral);
            String customerPassword = resultSet.getString(customerPasswordLiteral);

            customer = new Customer(customerId, customerName, customerContact, customerEmail, customerPassword);
        }
        return customer;
    }

}
