package com.java8.example.garage.task;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class SensorIn implements Runnable {

    /**
     * Counter for vehicle
     */
    private GarageCounter counter;

    public SensorIn(GarageCounter counter){
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.vehicleIn();
        counter.vehicleIn();
        counter.vehicleIn();
    }
}
