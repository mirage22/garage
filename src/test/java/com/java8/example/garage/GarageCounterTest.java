package com.java8.example.garage;

import com.java8.example.garage.task.GarageCounter;
import com.java8.example.garage.task.TestSensorIn;
import com.java8.example.garage.task.TestSensorOut;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * The MIT License
 *
 * Copyright 2014 Miroslav Kopecky.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
