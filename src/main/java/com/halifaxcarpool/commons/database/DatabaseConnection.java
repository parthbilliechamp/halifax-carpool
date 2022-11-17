package com.halifaxcarpool.commons.database;


import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static Connection connection = null;

    @Value("${spring.datasource.password}")
    private static String password;

    @Value("${spring.datasource.username}")
    private static String userName;

    @Value("${spring.datasource.url}")
    private static String url;

    private DatabaseConnection() {

    }

    public static Connection getConnectionInstance() {
        if (null == connection) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_12_DEVINT", "CSCI5308_12_DEVINT_USER", "beRuqMq7cG");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
