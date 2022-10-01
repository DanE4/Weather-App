package com.example.weatherapp;

public class City_notJPA implements java.io.Serializable {
    public City_notJPA() {
    }

    // Create database: weatherapp 1
    // Create table: weatherdata 1
    // cityname - name of the city 0.
    // temperature - the last known temperature of the city 1
    // time - the time of the measurement 1
    public City_notJPA(String cityname, String temperature, String time) {
        this.name = cityname;
        this.temp = temperature;
        this.time = time;
    }

    private String name;
    private String temp;
    private String time;

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
                ", temp=" + temp +
                ", time='" + time + '\'' +
                '}';
    }
}

