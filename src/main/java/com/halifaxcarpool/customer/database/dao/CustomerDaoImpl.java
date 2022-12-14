package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CustomerDaoImpl extends IUserDao {

    IDatabase database;
    Connection connection;

    public CustomerDaoImpl() {
        database = new DatabaseImpl();
    }

    public void registerUser(User user) throws Exception{
        Customer customer = (Customer) user;
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL insert_customer_details(?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getCustomerContact());
            statement.setString(3, customer.getCustomerEmail());
            statement.setString(4, customer.getCustomerPassword());
            statement.execute();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            database.closeDatabaseConnection();
        }
    }

    public boolean updateUser(User user) {
        Customer customer = (Customer) user;
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL update_customer_details(?,?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, customer.getCustomerId());
            statement.setString(2, customer.getCustomerName());
            statement.setString(3, customer.getCustomerContact());
            statement.setString(4, customer.getCustomerEmail());
            statement.setString(5, customer.getCustomerPassword());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

}
