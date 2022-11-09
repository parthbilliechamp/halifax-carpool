package com.halifaxcarpool.database;

/**
 * A contract to connect to database server.
 */
public interface IDatabase {

    void openDatabaseConnection();

    void closeDatabaseConnection();

}
