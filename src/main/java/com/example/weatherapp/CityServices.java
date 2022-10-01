package com.example.weatherapp;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ejb.Schedule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
public class CityServices {
    static String apiKey = "6aeed60b576be60e3b798b2489a881e1";
    static MongoDBProducer mongoDB = new MongoDBProducer();
    static MongoClient mongoClient = mongoDB.createMongo();
    static MongoDatabase db = mongoClient.getDatabase("weatherapp");
    static MongoCollection<Document> col = db.getCollection("weatherdata");
    public void eraseDB() {
        col.deleteMany(new Document());
    }

    public City_notJPA cityprops(String city) throws IOException {
        CityServices service = new CityServices();
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
        var finaltemp=  Double.parseDouble(dr.format((((temp-32)*5)/9)/10)
                .replace(",","."));
        var time=java.time.LocalDateTime.now();
        var timefinal=time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth()+" "+time.getHour()+":"+time.getMinute()+":"+time.getSecond();
        City_notJPA tempCityNotJPA = new City_notJPA(city, finaltemp +" Celsius", timefinal);
        service.list().stream().filter(c -> c.getName().equals(city)).findFirst().ifPresentOrElse(
                CityServices::update,
                () -> {
                    try {
                        service.add(city);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        return tempCityNotJPA;
    }


    public List<City_notJPA> list(){
        List<City_notJPA> list = new ArrayList<>();
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                list.add(new City_notJPA(document.getString("name"),
                        document.getString("temperature"),
                        document.getString("date")));
            }
        };
        col.find().forEach(printBlock);
        return list;
    }

    public void add(String city) throws IOException {
        CityServices service = new CityServices();
        var tempcity = service.cityprops(city);
        Document doc = new Document("name", tempcity.getName())
                .append("temperature", tempcity.getTemp())
                .append("date", tempcity.getTime());
        col.insertOne(doc);
    }

    public static void update(City_notJPA cityNotJPA){
        //update the city's temperature and time
        col.updateOne(new Document("name", cityNotJPA.getName()), set("temp", cityNotJPA.getTemp()));
        col.updateOne(new Document("name", cityNotJPA.getName()), set("time", cityNotJPA.getTime()));
        System.out.println(cityNotJPA.getName() + " updated");
    }

    public void delete(String city){
        col.deleteOne(eq("name",city));
    }

    private static MongoCollection<Document> getCollection(){
        return db.getCollection("weatherdata");
    }

    public void schedule() {
        var apiKey = "6aeed60b576be60e3b798b2489a881e1";
            try {
                List<City_notJPA> list = list();
                for (City_notJPA c : list) {
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + c.getName() + "&appid=" + apiKey;
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
                    update(new City_notJPA(c.getName(), finaltemp +" Celsius", timefinal));
                    System.out.println("Updated "+c.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
