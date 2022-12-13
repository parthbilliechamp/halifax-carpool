package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.sql.*;

public class CustomerAuthenticationDaoImpl implements IUserAuthenticationDao {

    IDatabase database;
    Connection connection;

    public CustomerAuthenticationDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public Customer authenticate(String username, String password) {
        ResultSet resultSet;
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL login_customer(?, ?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, username);
            stmt.setString(2, password);
            resultSet = stmt.executeQuery();

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
