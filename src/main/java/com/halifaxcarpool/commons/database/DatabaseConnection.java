package com.halifaxcarpool.commons.database;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE_= "application.properties";
    private static DatabaseConnection databaseConnection = null;
    private Connection connection;
    private String password;
    private String userName;
    private String url;

    private DatabaseConnection() {
        populateDataBaseConfiguration();
    }

    public static DatabaseConnection getDatabaseConnectionInstance() {
        if (null == databaseConnection) {
            databaseConnection = new DatabaseConnection();
        }
        return databaseConnection;
    }

    public Connection openDbConnection() {
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeDbConnection() {
        if (null != connection) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void populateDataBaseConfiguration() {
        String userProperty = "spring.datasource.username";
        String passwordProperty = "spring.datasource.password";
        String urlProperty = "spring.datasource.url";
        try {
            Properties properties = new Properties();
            InputStream inputStream =
                    Files.newInputStream(Paths.get(
                            Objects.requireNonNull(
                                    this.getClass().getClassLoader().getResource(PROPERTIES_FILE_)).getPath()));
            properties.load(inputStream);
            userName = properties.getProperty(userProperty);
            password = properties.getProperty(passwordProperty);
            url = properties.getProperty(urlProperty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
