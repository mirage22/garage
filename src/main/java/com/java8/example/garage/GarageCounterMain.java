package com.java8.example.garage;

import com.java8.example.garage.task.GarageCounter;
import com.java8.example.garage.task.SensorIn;
import com.java8.example.garage.task.SensorOut;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class GarageCounterMain {

    public static void main(String... args){

        GarageCounter counter = new GarageCounter(3);

        SensorIn sensorIn = new SensorIn(counter);
        SensorOut sensorOut = new SensorOut(counter);

        Thread sensorInThread = new Thread(sensorIn);
        Thread sensorOutThread = new Thread(sensorOut);

        sensorInThread.start();

        System.out.printf("GarageCounterMain: Number of cars: %d\n",counter.get());

        sensorOutThread.start();

        try{
            sensorInThread.join();
            sensorOutThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        counter.vehicleIn();
        counter.vehicleIn();


        System.out.printf("GarageCounterMain: Number of cars: %d\n",counter.get());
        System.out.printf("GarageCounterMain: End of the program.\n");


    }

}
