package com.example.weatherapp;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class City implements Serializable {

    @Id
    @Column
    private String name;

    @Column
    private String temp;
    @Column
    private String time;
    public City(String cityname, String temperature, String time) {
        this.name = cityname;
        this.temp = temperature;
        this.time = time;
    }

    public City() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", temp='" + temp + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}