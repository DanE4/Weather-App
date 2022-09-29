package com.example.weatherapp;

import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoClientConnectionExample {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase db = mongoClient.getDatabase("weatherapp");
        MongoCollection<Document> col = db.getCollection("weatherdata");
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = db.runCommand(command);
                System.out.println("Connected successfully to server.");
        }
    }

