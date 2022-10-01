package com.example.weatherapp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@Path("/city")
@Consumes("application/json")
@Produces("application/json")
public class CityRestApi {
        @Inject
        CityServices services;
    @GET
    @Path("/")
    @Produces({MediaType.TEXT_PLAIN})
    public Response index() {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity("")
                .build();
    }
        @GET
        @Path("/{city}")
        @Produces(MediaType.APPLICATION_JSON)
        //http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/city/Budapest
        public City cityProps(@PathParam("city") String city) throws  SQLException {
            return services.cityprops(city);
        }
        @POST
        @Path("/add/{city}")
        public void addCity(@PathParam("city") String city) throws IOException, SQLException {
            services.addCity(city);
        }
        @GET
        @Path("/list-cities")
        @Produces(MediaType.APPLICATION_JSON)
        //http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/city/list-cities
        public List<City> listCities() throws SQLException, IOException {
            return services.listCities();
        }
        @DELETE
        @Path("/delete/{city}")
        //http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/city/delete/Budapest
        public void deleteCity(@PathParam("city") String city) throws SQLException {
            services.deleteCity(city);
        }
    }
