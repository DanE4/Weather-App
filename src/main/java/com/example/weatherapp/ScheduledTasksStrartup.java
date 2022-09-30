package com.example.weatherapp;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Logger;
@Singleton
@Startup
public class ScheduledTasksStrartup {
    private static final Logger LOGGER = Logger.getLogger(ScheduledTasksStrartup.class.getName());
    @EJB
    ScheduledThings scheduledThings;
    @Schedule(second = "*/2", minute = "*", hour = "*", persistent = false)
    public void showCurrentTime() {
        System.out.println("Current time: " + java.time.LocalTime.now());

    }
}
