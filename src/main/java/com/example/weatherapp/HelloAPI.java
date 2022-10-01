package com.example.weatherapp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import javax.enterprise.inject.New;
import java.io.IOException;
import java.sql.SQLException;

@Path("/hello")
public class HelloAPI {
    @Inject
    NewServices services;

    @GET
    @Path("/hello_weather")
    @Produces("text/plain")
    public String hello() {
        return "Hello, Weather!";
    }
    @GET
    @Path("/{name}")
    @Consumes("application/json")
    @Produces("application/json")
    public String customWelcome(@PathParam("name") String name) {
        return "Welcome " + name;
    }
    //POST http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/hello/Dani

}
