package com.example.weatherapp;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


public class CityService {
    static MongoDBProducer mongoDBProducer = new MongoDBProducer();
    static MongoClient mongoClient = mongoDBProducer.createMongo();
    static MongoDatabase db = mongoClient.getDatabase("weatherapp");

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
    public void add(City city){
        Document document = new Document()
                .append("name", city.getCityname())
                .append("temp", city.getTemperature())
                .append("time", city.getTime());
        getCollection().insertOne(document);
    }
    public void update(City city){
        Bson filter = eq("name", city.getCityname());
        Bson updateOperation = set("name", city.getCityname());
        getCollection().updateOne(filter, updateOperation);
    }
    public void delete(City city){
        Bson filter = eq("name", city.getCityname());
        getCollection().deleteOne(filter);
    }
    private MongoCollection<Document> getCollection(){

        return db.getCollection("weatherdata");
    }
}
