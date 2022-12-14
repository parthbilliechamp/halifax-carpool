package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;

import java.sql.*;

public class AdminAuthenticationDaoImpl implements IAdminAuthenticationDao {

    IDatabase database;
    Connection connection;

    public AdminAuthenticationDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public Admin authenticate(String userName, String password) {
        ResultSet resultSet;

        try {
            connection = database.openDatabaseConnection();
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
            String adminIdLiteral = "adminId";
            int adminId = Integer.parseInt(resultSet.getString(adminIdLiteral));
            String adminUsernameLiteral = "admin_username";
            String userName = resultSet.getString(adminUsernameLiteral);
            String adminPasswordLiteral = "admin_password";
            String password = resultSet.getString(adminPasswordLiteral);
            admin = new Admin(adminId, userName, password);
        }
        return admin;
    }
}
