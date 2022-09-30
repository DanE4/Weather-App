package com.example.weatherapp;

import javax.ejb.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
@Stateless
@LocalBean
public class ScheduledThings {
    @Schedule(second = "*/2", minute = "*", hour = "*", persistent = false)
    public void showCurrentTime() {
        System.out.println("Current time: " + java.time.LocalTime.now());
    }
}
