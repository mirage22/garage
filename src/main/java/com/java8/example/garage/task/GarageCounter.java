package com.java8.example.garage.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
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
 *
 * Implementing AtomicInteger and extending
 * operation should be done at one step.
 *
 */
public class GarageCounter extends AtomicInteger {

    private static final Logger logger = LoggerFactory.getLogger(GarageCounter.class);

    private static final long serialVersionUID = 22L;

    /**
     * Maximum number of parking spaces
     */
    private int maxNumber;

    public GarageCounter(int maxNumber){
        set(0);
        this.maxNumber = maxNumber;
    }

    public boolean vehicleIn(){
        int value = get();
        if(value == maxNumber){
            logger.debug("GarageCounter: The parking is FULL.");
            return false;
        } else {
            int tmpValue = value + 1;
            boolean changed = compareAndSet(value, tmpValue);
            if(changed){
                logger.debug("GarageCounter: A Vehicle has ENTERED.");
                return true;
            }
        }
        return false;
    }

    public boolean vehicleOut(){
        int value = get();
        if(value == 0){
            logger.debug("GarageCounter: The parking is EMPTY.");
            return false;
        } else {
            int tmpValue = value - 1;
            boolean changed = compareAndSet(value, tmpValue);
            if(changed){
                logger.debug("GarageCounter: A Vehicle has gone OUT.");
            }
        }
        return false;
    }

}
