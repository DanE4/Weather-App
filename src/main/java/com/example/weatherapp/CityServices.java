package com.example.weatherapp;
import javax.ejb.Stateless;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CityServices {
    static String apiKey = "replace with your api key";
    static Connection connection;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found !!");
        }
    }
    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weatherapp?allowPublicKeyRetrieval=true&useSSL=false","root", "Rootpassword");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public City fetchData(String city) throws SQLException, IOException {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        var str=client.sendAsync(getRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        var l=str.split("\"temp\":");
        var l2=l[1].split(",");
        var temp= Double.parseDouble(l2[0]);
        DecimalFormat dr = new DecimalFormat("#.##");
        var finaltemp=  Double.parseDouble(dr.format(temp-273.15)
                .replace(",","."));
        var time=java.time.LocalDateTime.now();
        var timefinal=time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth()+" "
                +time.getHour()+":"+time.getMinute()+":"+time.getSecond();
        City c=new City(city,finaltemp+" °C",timefinal);
        update(c);
        return new City(city, finaltemp +" °C", timefinal);
    }


    public void addCity(String city) throws SQLException, IOException {
        City tempCity = fetchData(city);
        var resultSet = connection.createStatement().executeQuery("SELECT * FROM weatherapp.weatherdata WHERE cityname = '" + city + "'");
        if (!resultSet.next()) {
            var sql = "INSERT INTO weatherdata(cityName, temperature,time) VALUES ('" + tempCity.getName() + "', '" + tempCity.getTemp() + "', '" + tempCity.getTime() + "')";
            connection.createStatement().execute(sql);
        }
        else {
            update(tempCity);
        }
    }
    public  void update(City city) throws SQLException {
        var sql = "UPDATE weatherdata SET temperature = '" + city.getTemp() + "', time = '" + city.getTime() + "' WHERE cityName = '" + city.getName() + "'";
        connection.createStatement().execute(sql);
    }

    public void deleteCity(String city) throws SQLException {
        connection.createStatement().execute("DELETE FROM weatherapp.weatherdata WHERE cityname = '" + city + "'");
    }

    public City cityprops(String city) throws SQLException {
        var resultSet = connection.createStatement().executeQuery("SELECT * FROM weatherapp.weatherdata WHERE cityname = '" + city + "'");
        if (resultSet.next()) {
            return new City(resultSet.getString("cityname"), resultSet.getString("temperature"), resultSet.getString("time"));
        }
        return null;
}

    public List<City> listCities() throws SQLException, IOException {
        List<City> list = new ArrayList<>();
        var resultSet = connection.createStatement().executeQuery("SELECT * FROM weatherapp.weatherdata");
        while (resultSet.next()) {
            City tempCity=fetchData(resultSet.getString("cityname"));
            list.add(tempCity);
        }
        return list;
    }
    public void eraseDB() throws SQLException {
        connection.createStatement().execute("DELETE FROM weatherapp.weatherdata");
    }
}
