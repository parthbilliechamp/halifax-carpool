package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.*;

public class AdminAuthenticationDaoImpl implements IAdminAuthenticationDao {

    IDatabase database;
    Connection connection;

    public AdminAuthenticationDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    @Override
    public Admin authenticate(String userName, String password) {
        ResultSet resultSet;

        try {
            String SQL_STRING = "{CALL login_admin(?, ?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, userName);
            stmt.setString(2, password);
            resultSet = stmt.executeQuery();
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
