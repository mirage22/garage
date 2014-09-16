package com.java8.example.garage;

import com.java8.example.garage.handler.SensorExceptionHandler;
import com.java8.example.garage.task.ExceptionTask;
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
