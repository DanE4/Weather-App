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
import java.text.DecimalFormat;

public class Client {
    static Connection connection;
    static String apiKey = "3e9375c46981cc9b81782c9a5daf7f1b";

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weatherapp?allowPublicKeyRetrieval=true&useSSL=false","root", "Rootpassword");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException, IOException, InterruptedException, URISyntaxException {

var city ="Budapest";

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        var str=client.sendAsync(getRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        var l=str.split("\"temp\":");
        System.out.println(l[0]);

        var l2=l[1].split(",");
        var temp= Double.parseDouble(l2[0]);
        DecimalFormat dr = new DecimalFormat("#.##");
        var finaltemp=  Double.parseDouble(dr.format(temp-273.15)
                .replace(",","."));
        var time=java.time.LocalDateTime.now();
        var timefinal=time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth()+" "
                +time.getHour()+":"+time.getMinute()+":"+time.getSecond();
        System.out.println(city+" "+finaltemp+" "+timefinal);


/*
        CityServices services = new CityServices();
        String url = "http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/city/add/Budapest";

        System.out.println(services.listCities());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url)).POST(HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response);
        System.out.println(services.listCities());
        */

    }
}
