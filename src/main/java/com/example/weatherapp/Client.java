package com.example.weatherapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Provider;
import java.text.DecimalFormat;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
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

            //System.out.println(str);
            var l=str.split("\"temp\":");
            //System.out.println(l[0]+" \n"+l[1]);
            var l2=l[1].split(",");
            //System.out.println(l[1]);
            //System.out.println(l2[0]);

            var temp= Double.parseDouble(l2[0]);
            DecimalFormat dr = new DecimalFormat("#.##");
            System.out.println(dr.format((((temp-32)*5)/9)/10)+"°C");




        }
        catch (IOException e) {
            System.out.println("Could not listen on port: 8080");
            System.exit(-1);
        }

    }
}

