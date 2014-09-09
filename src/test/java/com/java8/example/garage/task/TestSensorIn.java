package com.java8.example.garage.task;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class TestSensorIn implements Runnable {

    /**
     * Counter for vehicle
     */
    private GarageCounter counter;

    private int maxInt;

    public TestSensorIn(GarageCounter counter, int maxInt){
        this.counter = counter;
        this.maxInt = maxInt;
    }

    @Override
    public void run() {
        for(int i=0; i<maxInt; i++){
            counter.vehicleIn();
        }
    }
}
