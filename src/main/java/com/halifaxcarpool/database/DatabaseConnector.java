package com.halifaxcarpool.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection getConnection(String url, String userName, String password) throws URISyntaxException, SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

}
