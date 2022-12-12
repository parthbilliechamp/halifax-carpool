package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDaoImpl extends IUserDao {

    IDatabase database;
    Connection connection;

    public void registerUser(User user) throws Exception{
        Customer customer = (Customer) user;
        try {
            database = new DatabaseImpl();
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL insert_customer_details(?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, customer.getCustomerName());
            stmt.setString(2, customer.getCustomerContact());
            stmt.setString(3, customer.getCustomerEmail());
            stmt.setString(4, customer.getCustomerPassword());
            stmt.execute();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            database.closeDatabaseConnection();
        }
    }

    public boolean updateUser(User user) {
        Customer customer = (Customer) user;
        try {
            database = new DatabaseImpl();
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

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }

        return false;
    }
}
