package com.java8.example.garage.task;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class GarageCounter extends AtomicInteger {

    private static final long serialVersionUID = 22L;

    private int maxNumber;

    public GarageCounter(int maxNumber){
        set(0);
        this.maxNumber = maxNumber;
    }

    public boolean vehicleIn(){
        int value = get();
        if(value == maxNumber){
            System.out.printf("GarageCounter: The parking is full.\n");
            return false;
        } else {
            int tmpValue = value + 1;
            boolean changed = compareAndSet(value, tmpValue);
            if(changed){
                System.out.printf("GarageCounter: A Vehicle has entered.\n");
                return true;
            }
        }
        return false;
    }

    public boolean vehicleOut(){
        int value = get();
        if(value == 0){
            System.out.printf("GarageCounter: The parking is empty.\n");
            return false;
        } else {
            int tmpValue = value - 1;
            boolean changed = compareAndSet(value, tmpValue);
            if(changed){
                System.out.printf("GarageCounter: A Vehicle has gone out.\n");
            }
        }
        return false;
    }

}
