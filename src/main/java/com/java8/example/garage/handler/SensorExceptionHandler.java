package com.java8.example.garage.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * process the uncaught exception throwed inside any Sensor Thread
 * Catching Execption insde the RUN because Run doesn't accept throw.
 * Default run() behaviour is write to the stack and exit the program
 *
 */
public class SensorExceptionHandler implements UncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(SensorExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("An Sensor exception has been captured");
        logger.error("Thread: " + t.getId());
        logger.error("Exception: "+ e.getClass().getName() +" " + e.getMessage());
        logger.error("Stack Trace: " + e);
        logger.error("Thread status: " + t.getState());
    }

}
