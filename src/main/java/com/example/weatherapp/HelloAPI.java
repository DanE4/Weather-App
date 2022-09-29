package com.example.weatherapp;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello")
public class HelloAPI {
    //http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/hello/hello-weather
    @GET
    @Path("/hello_weather")
    @Produces("text/plain")
    public String hello() {
        return "Hello, Weather!";
    }
}
