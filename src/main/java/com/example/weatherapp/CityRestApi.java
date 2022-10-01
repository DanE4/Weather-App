package com.example.weatherapp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/hello/hello-????
  /*

    @GET
    @Path("/hello-dani")
    @Produces("text/plain")
    public String hello2() {
        return "Hello, DANI!";
    }
    @POST
    @Path("/{name}")
    @Produces("text/plain")
    public String customWelcome(@PathParam("name") String name) {
        return "Welcome " + name;
    }
    @Path("/customer")
    @Produces("application/json")
    @Consumes("application/json")
    */

//RESTAPIS
@Path("/city")
//http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/city/Budapest

@Consumes("application/json")
@Produces("application/json")
public class CityRestApi {
        @Inject
        NewServices services;
        @GET
        @Path("/{city}")
        @Produces(MediaType.APPLICATION_JSON)
        //http://localhost:8080/Weather-App-1.0-SNAPSHOT/api/weather/Budapest
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
        public List<City> listCities() throws SQLException {
            return services.listCities();
        }
        @DELETE
        @Path("/delete/{city}")
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


