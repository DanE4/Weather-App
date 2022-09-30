package com.example.weatherapp;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Path("/cities")
public class CityServices {
    static MongoDBProducer mongoDB = new MongoDBProducer();
    static MongoClient mongoClient = mongoDB.createMongo();
    static MongoDatabase db = mongoClient.getDatabase("weatherapp");
    static MongoCollection<Document> col = db.getCollection("weatherdata");
    public void eraseDB() {
        col.deleteMany(new Document());
    }
    @GET
    @Path("/list-cities")
    @Produces("text/plain")
    public List<City> list(){
        List<City> list = new ArrayList<>();
        try {
            Bson filter = Filters.exists("name");
            getCollection().find(filter).forEach((Block<? super Document>) document -> {
                City city = new City( document.getString("name"), document.getInteger("temp"),document.getString("time"));
                city.setCityname(document.getString("name"));
                city.setTemperature(document.getInteger("temp"));
                city.setTime(document.getString("time"));
                list.add(city);
            });
        }
        catch (Exception exc ) {
            exc.printStackTrace();
        }
        return list;
    }
    //schedule a function to run every 5 minutes
    @POST
    @Path("/add")
    public void add(City city){
        Document document = new Document()
                .append("name", city.getCityname())
                .append("temp", city.getTemperature())
                .append("time", city.getTime());
        getCollection().insertOne(document);
    }
    @POST
    @Path("/update")
    public static void update(City city){
        Bson filter = eq("name", city.getCityname());
        Bson updateOperation = set("name", city.getCityname());
        getCollection().updateOne(filter, updateOperation);
    }
    @DELETE
    @Path("/delete")
    public void delete(City city){
        Bson filter = eq("name", city.getCityname());
        getCollection().deleteOne(filter);
    }

    private static MongoCollection<Document> getCollection(){
        return db.getCollection("weatherdata");
    }
    @Schedule(second = "*/5")
    public void schedule() {
        var apiKey = "0dcf4ad697f6a1c9f2681e1c327af69f";
            try {
                List<City> list = list();
                for (City c : list) {
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + c.getCityname() + "&appid=" + apiKey;
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
                    var finaltemp=  Double.parseDouble(dr.format((((temp-32)*5)/9)/10)
                            .replace(",","."));
                    var time=java.time.LocalDateTime.now();
                    var timefinal=time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth()+" "+time.getHour()+":"+time.getMinute()+":"+time.getSecond();
                    update(new City(c.getCityname(), finaltemp, timefinal));
                    System.out.println("Updated "+c.getCityname());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
