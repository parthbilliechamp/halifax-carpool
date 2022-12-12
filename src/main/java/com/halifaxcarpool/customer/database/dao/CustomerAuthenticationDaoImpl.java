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
        connection = database.openDatabaseConnection();
    }

    @Override
    public Customer authenticate(String username, String password) {
        ResultSet resultSet;
        try {
            String SQL_STRING = "{CALL login_customer(?, ?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, username);
            stmt.setString(2, password);
            resultSet = stmt.executeQuery();

            return buildCustomerFrom(resultSet);
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

    private static Customer buildCustomerFrom(ResultSet resultSet) throws SQLException {

        Customer customer = null;
        while (resultSet.next()) {
            int customer_id = Integer.parseInt(resultSet.getString("customer_id"));
            String customer_name = resultSet.getString("customer_name");
            String customer_contact = resultSet.getString("customer_contact");
            String customer_email = resultSet.getString("customer_email");
            String customer_password = resultSet.getString("customer_password");
            customer = new Customer(customer_id, customer_name, customer_contact, customer_email, customer_password);
        }
        return customer;
    }

}
