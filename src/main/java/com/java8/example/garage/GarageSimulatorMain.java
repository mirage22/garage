package com.java8.example.garage;

import com.java8.example.garage.model.CarVehicle;
import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.MotoVehicle;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.service.GarageService;
import com.java8.example.garage.service.GarageServiceImpl;
import com.java8.example.garage.task.GarageCounter;
import com.java8.example.garage.task.GarageSensorIn;
import com.java8.example.garage.task.GarageSensorOut;
import com.java8.example.garage.task.SensorIn;

import java.util.Arrays;

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

        String[] licences1 = {"1", "2"};
        Vehicle[] inVehicle1 = {new MotoVehicle("1"), new CarVehicle("2")};

        String[] licences2 = {"3", "4", "6"};
        Vehicle[] inVehicle2 = {new MotoVehicle("3"), new CarVehicle("4"), new CarVehicle("6")};

        String[] licences3 = {"5"};
        Vehicle[] inVehicle3 = {new MotoVehicle("5")};

        GarageSensorIn sensorIn1 = new GarageSensorIn(garage, licences1);
        Thread sensorInThread1 = new Thread(sensorIn1);
        sensorInThread1.start();

        GarageSensorIn sensorIn2 = new GarageSensorIn(garage, licences2);
        Thread sensorInThread2 = new Thread(sensorIn2);
        sensorInThread2.start();

        GarageSensorIn sensorIn3 = new GarageSensorIn(garage, licences3);
        Thread sensorInThread3 = new Thread(sensorIn3);
        sensorInThread3.start();

        try{
            sensorInThread1.join();
            sensorInThread2.join();
            sensorInThread3.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("END Garage maxCapacity = " + garage.getMaxCapacity());
        System.out.println("END Garage cars = " + garage.getCounter().get());

        System.out.println("END Garage status = " + garage.isFree());
        ParkingPlace[][] parkingPlace = garage.getPlaces();
        for(int i=0; i < garage.getMaxLevel(); i++){
            for(int j=0; j < garage.getMaxSlot(); j++){
                System.out.println("END GarageParkingPlace [" + i + "]" + "[" + j + "] - " + parkingPlace[i][j].getVehicle());
            }
        }

        //Remove Vehicle
        Vehicle[] removeVehicle1 = {new MotoVehicle("1", true)};

        GarageSensorOut sensorOut1 = new GarageSensorOut(garage, removeVehicle1);
        Thread sensorOutThread1 = new Thread(sensorOut1);
        sensorOutThread1.start();

        try{
            sensorOutThread1.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("END1 Garage status = " + garage.isFree() + " freeSpace = " + garage.getFreeSpaces());
        ParkingPlace[][] parkingPlace1 = garage.getPlaces();
        for(int i=0; i < garage.getMaxLevel(); i++){
            for(int j=0; j < garage.getMaxSlot(); j++){
                System.out.println("END GarageParkingPlace [" + i + "]" + "[" + j + "] - " + parkingPlace1[i][j].getVehicle());
            }
        }

        ParkingPlace findPlace1 = garage.getParkingPlaceByLicense("3");
        if(findPlace1 != null){
            System.out.println("FIND1 MOTOR BIKE = " + findPlace1.getVehicle() + " level= " + findPlace1.getLevel() + " slot= " + findPlace1.getSlot());
        }

        ParkingPlace findPlace2 = garage.getParkingPlaceByLicense("1");
        if(findPlace2 != null){
            System.out.println("FIND2 MOTOR BIKE = " + findPlace2.getVehicle() + " level= " + findPlace2.getLevel() + " slot= " + findPlace2.getSlot());
        }

    }
}
