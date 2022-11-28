package com.halifaxcarpool.commons.database;

import java.sql.Connection;

public class DatabaseImpl implements IDatabase {

    private final DatabaseConnection databaseConnection;

    public DatabaseImpl() {
        databaseConnection = DatabaseConnection.getDatabaseConnectionInstance();
    }

    @Override
    public Connection openDatabaseConnection() {
        return databaseConnection.openDbConnection();
    }

    @Override
    public void closeDatabaseConnection() {
        databaseConnection.closeDbConnection();
    }

}
