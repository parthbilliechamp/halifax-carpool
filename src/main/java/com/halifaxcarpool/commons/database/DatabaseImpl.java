package com.halifaxcarpool.commons.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseImpl implements IDatabase {

    private final Connection connection;

    public DatabaseImpl() {
        connection = DatabaseConnection.getConnectionInstance();
    }

    @Override
    public Connection openDatabaseConnection() {
        return connection;
    }

    @Override
    public void closeDatabaseConnection() {
        try {
            if (null != connection) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
