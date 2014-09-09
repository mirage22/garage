package com.java8.example.garage;

import com.java8.example.garage.handler.SensorExceptionHandler;
import com.java8.example.garage.task.ExceptionTask;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by miroslavkopecky on 09/09/14.
 */
public class SensorExceptionHandlerTest {

    ExceptionTask exceptionTask;
    Thread testThread;

    @Before
    public void setUp(){
        exceptionTask = new ExceptionTask();
        testThread = new Thread(exceptionTask);
    }


    @Test
    public void testCounterFunctionality(){
        testThread.setUncaughtExceptionHandler(new SensorExceptionHandler());
        assertNotNull(testThread);

        //Start testThread
        testThread.start();

        try {
            testThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
