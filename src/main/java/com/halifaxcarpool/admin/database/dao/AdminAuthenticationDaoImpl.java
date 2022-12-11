package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminAuthenticationDaoImpl implements IAdminAuthenticationDao {

    IDatabase database;
    Connection connection;

    public AdminAuthenticationDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    @Override
    public Admin authenticate(String userName, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            resultSet = statement.executeQuery("CALL login_admin('" + userName + "', '" + password + "')");
            return buildAdminFrom(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

    private static Admin buildAdminFrom(ResultSet resultSet) throws SQLException {
        Admin admin = null;
        while (resultSet.next()) {
            int adminId = Integer.parseInt(resultSet.getString("adminId"));
            String userName = resultSet.getString("admin_username");
            String password = resultSet.getString("admin_password");
            admin = new Admin(adminId, userName, password);
        }

        return admin;
    }
}
