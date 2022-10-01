package com.example.weatherapp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@Path("/city")
//http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/city/Budapest

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
//how to make a POST request


    /*
    Database	Create database schema and tables	Create the necesary database schema and the tables.	--

    Backend	Create entity class and basic JPA functions	Create model for watherdata and save it, load it from database successfully.


Backend	Hello world Rest API	Create a simple REST API which returns only "Hello Weather"  OKKKKKKKKKKKK
Backend	Rest API: addCity	Implement and test the addCity API. Save data to database.	OKKKKKKKKKKKK
Backend	Rest API: listCities	Implement and test listCities API. Have to load data from local DB.OKKKKKKKKKKKK
Backend	Rest API: deleteCity	Implement and test deleteCity API. Have to delete data from local DB.	OKKKKKKKKKKKK
Backend	openweathermap API client	Implement a client that calls openweatherapi.org weather REST API by city name. The
result must be saved to our local database.	--

Backend	Scheduled update service	Implement a the update service which update the cities data from the openweather api.	--
Backend	Dependency Injection	Use DI to use Java EE components: update service (EJB), entityManager (JPA) etc...	--
Backend	Error handling	Handle errors in the Rest API with general way.	--
Backend	Logging	Log the events what happens in the REST API.	--
    */
    /*

    @GET
     */


