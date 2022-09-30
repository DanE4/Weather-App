package com.example.weatherapp;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
public class Client {
    public static void main(String[] args) throws IOException {
            CityServices service = new CityServices();
            service.eraseDB();
            var apiKey = "0dcf4ad697f6a1c9f2681e1c327af69f";
            var city = "Budapest";
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

            Socket socket = new Socket("localhost", 8080);
            System.out.println("Connected to server");
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
            System.out.println(dr.format((((temp-32)*5)/9)/10)+"°C");
            var finaltemp=  Double.parseDouble(dr.format((((temp-32)*5)/9)/10)
                    .replace(",","."));
            var time=java.time.LocalDateTime.now();

            System.out.println(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth()+" "+time.getHour()+":"+time.getMinute()+":"+time.getSecond());
            var timefinal=time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth()+" "+time.getHour()+":"+time.getMinute()+":"+time.getSecond();
            service.add(new City(city, finaltemp, timefinal));
            System.out.println("END");
        }
}

