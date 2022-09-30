package com.example.weatherapp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationPath("/api")
public class AppTEST extends Application {
    static MongoDBProducer mongoDB = new MongoDBProducer();
    static MongoClient mongoClient = mongoDB.createMongo();
    static MongoDatabase db = mongoClient.getDatabase("weatherapp");
    static MongoCollection<Document> col = db.getCollection("weatherdata");

    public static void main(String[] args) {
        col.deleteMany(new Document());
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        Bson command = new BsonDocument("ping", new BsonInt64(1));
        Document commandResult = db.runCommand(command);
        System.out.println("Connected successfully to server.");
        System.out.println(commandResult.toJson());

        City city = new City("Budapest", 20.0, "2021-05-05 12:00:00");
        CityServices service = new CityServices();
        service.add(city);
        service.list().forEach(System.out::println);

        System.out.println("COOOL");
    }
}
