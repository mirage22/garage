package com.java8.example.garage.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

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
