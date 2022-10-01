package com.example.weatherapp;

import jakarta.ws.rs.Produces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBProducer {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/weatherapp";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Fasztarko";
    @Produces
    public static Connection createDB() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    public static String getDbUrl() {
        return DB_URL;
    }
    public static String getDbUser() {
        return DB_USER;
    }
    public static String getDbPassword() {
        return DB_PASSWORD;
    }
}
