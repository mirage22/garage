package com.java8.example.garage;

import com.java8.example.garage.task.GarageCounter;
import com.java8.example.garage.task.TestSensorIn;
import com.java8.example.garage.task.TestSensorOut;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by miroslavkopecky on 08/09/14.
 */

public class GarageCounterTest {

    final int MAX_ITERATION = 4;
    final int ITERATION_DEC = 3;  //

    GarageCounter counter;
    int outSensorIterations;
    int expectedResult;


    @Before
    public void setUp(){
        counter = new GarageCounter(MAX_ITERATION);
        outSensorIterations = MAX_ITERATION - ITERATION_DEC;
    }

    @Test
    public void testCounterFunctionality(){

        assertNotNull(counter);

        TestSensorIn testSensorIn = new TestSensorIn(counter, MAX_ITERATION);
        TestSensorOut testSensorOut = new TestSensorOut(counter, outSensorIterations);
        assertNotNull(testSensorIn);
        assertNotNull(testSensorOut);

        Thread sensorInThread = new Thread(testSensorIn);
        Thread sensorOutThread = new Thread(testSensorOut);
        assertNotNull(sensorInThread);
        assertNotNull(sensorOutThread);

        try{
            sensorInThread.start();
            sensorOutThread.start();

            sensorInThread.join();
            sensorOutThread.join();

        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            assertTrue(counter.get() > 0);
        }

    }

}
