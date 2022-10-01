package com.example.weatherapp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
@Path("/hello-weather")
public class Hello {
    @Inject
    CityServices services;
    @GET
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
}
