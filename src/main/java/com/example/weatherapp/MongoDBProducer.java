package com.example.weatherapp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
public class MongoDBProducer {
    @Produces
    public MongoClient createMongo() {
        return  MongoClients.create("mongodb://localhost:27017");
    }
    @Produces
    public MongoDatabase createDB(MongoClient client) {

        return client.getDatabase("weatherapp");
    }
    @Produces
    public MongoCollection createCollection(MongoDatabase db) {
        return db.getCollection("weatherdata");
    }

    public void close(@Disposes MongoClient toClose) {
        toClose.close();
    }
}