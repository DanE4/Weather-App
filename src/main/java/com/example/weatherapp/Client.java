package com.example.weatherapp;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

import static java.lang.Thread.sleep;

public class Client {
    static String apiKey = "6aeed60b576be60e3b798b2489a881e1";
    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weatherapp?allowPublicKeyRetrieval=true&useSSL=false","root", "Fasztarko");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        NewServices services = new NewServices();
        services.addCity("Budapest");
        City tempcity = services.cityprops("Budapest");
        System.out.println("Cityprops_Service: "+services.cityprops("Budapest"));
        System.out.println(tempcity);

        services.addCity("Berlin");
        services.addCity("London");
        System.out.println("------------------------------------------------");
        System.out.println(services.listCities());


    }
}

