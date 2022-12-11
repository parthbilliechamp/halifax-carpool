package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDaoImpl extends IUserDao {

    IDatabase database;
    Connection connection;

    public CustomerDaoImpl() {

    }

    public void registerUser(User user) {
        Customer customerUser = (Customer) user;
        try {
            database = new DatabaseImpl();
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL insert_customer_details('"+ customerUser.getCustomerName() + "', '" + customerUser.getCustomerContact() + "', '" + customerUser.getCustomerEmail() + "', '" + customerUser.getCustomerPassword() + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

}
