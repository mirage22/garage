package com.java8.example.garage;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.service.GarageService;
import com.java8.example.garage.service.GarageServiceImpl;
import com.java8.example.garage.task.GarageCounter;
import com.java8.example.garage.task.SensorIn;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class GarageSimulatorMain {

    private static final int MAX_LEVEL = 2;
    private static final int MAX_SLOT = 2; //max slots per level

    public static void main(String... args){
        System.out.println("Garage Simulator");

        GarageService garageService = new GarageServiceImpl();

        int maxFreePlaces = MAX_LEVEL * MAX_SLOT;
        GarageCounter counter = new GarageCounter(maxFreePlaces);


        Garage garage = garageService.create(counter, MAX_LEVEL, MAX_SLOT);

        SensorIn sensorIn = new SensorIn(counter);
        Thread sensorInThread = new Thread(sensorIn);
        sensorInThread.start();

        try{
            sensorInThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("END Garage maxCapacity = " + garage.getMaxCapacity());
        System.out.println("END Garage cars = " + garage.getCounter().get());

        //Run it again
        sensorIn = new SensorIn(counter);
        sensorInThread = new Thread(sensorIn);
        sensorInThread.start();
        try{
            sensorInThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("END Garage maxCapacity = " + garage.getMaxCapacity());
        System.out.println("END Garage cars = " + garage.getCounter().get());



    }
}
