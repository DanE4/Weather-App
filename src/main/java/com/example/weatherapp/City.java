package com.example.weatherapp;

public class City {
    // Create database: weatherapp 1
    // Create table: weatherdata 1
    // cityname - name of the city 0.
    // temperature - the last known temperature of the city 1
    // time - the time of the measurement 1
    public City(String cityname, int temperature, String time) {
        this.cityname = cityname;
        this.temperature = temperature;
        this.time = time;
    }
    private String cityname;
    private int temperature;
    private String time;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
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
                "cityname='" + cityname + '\'' +
                ", temperature=" + temperature +
                ", time='" + time + '\'' +
                '}';
    }
}
