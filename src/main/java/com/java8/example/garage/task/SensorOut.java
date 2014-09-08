package com.java8.example.garage.task;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class SensorOut implements Runnable {

    /**
     * Counter for vehicle
     */
    private GarageCounter counter;

    public SensorOut(GarageCounter counter){
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.vehicleOut();
        counter.vehicleOut();
    }
}
