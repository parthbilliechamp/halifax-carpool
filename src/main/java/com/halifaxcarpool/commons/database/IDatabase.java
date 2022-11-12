package com.halifaxcarpool.commons.database;

import java.sql.Connection;

public interface IDatabase {

    Connection openDatabaseConnection();

    void closeDatabaseConnection();

}
