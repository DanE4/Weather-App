package com.example.weatherapp;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Client {
    static String apiKey = "replace with your api key";
    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weatherapp?allowPublicKeyRetrieval=true&useSSL=false","root", "Rootpassword");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException, IOException, InterruptedException, URISyntaxException {

        CityServices services = new CityServices();
        String url = "http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/city/add/Budapest";

        System.out.println(services.listCities());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url)).POST(HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response);
        System.out.println(services.listCities());
    }
}

