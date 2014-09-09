package com.java8.example.garage.task;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.service.GarageManagerServiceImpl;
import com.java8.example.garage.service.GarageService;
import com.java8.example.garage.service.GarageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by miroslavkopecky on 08/09/14.
 *
 * GarageSensorIn simulates the gate into the garage
 */
public class GarageSensorIn implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(GarageSensorIn.class);

    //Control the access to the sensor
    private final Lock lock;

    private Garage garage;

    private Vehicle[] vehicles;

    public GarageSensorIn(Garage garage, Vehicle[] vehicles){
        lock = new ReentrantLock();
        this.garage = garage;
        this.vehicles = vehicles;
    }

    //Simulate traffic int the gate into garage
    @Override
    public void run() {
        lock.lock();
        try{
            for(Vehicle vehicle: vehicles){
                if(vehicle != null && garage.getParkingPlaceByLicense(vehicle.getLicense()) == null){
                    garage = placeVehicle(garage, vehicle);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private  Garage placeVehicle(Garage garage, Vehicle vehicle) throws InterruptedException{
        if(garage.isFree()){

            GarageService garageService = GarageServiceImpl.getInstance();

            ParkingPlace parkingPlace = garageService.getFreeParkingPlace(garage);

            garageService.setParkingPlace(garage, parkingPlace, vehicle);

            garage.getCounter().vehicleIn();
        } else {
            logger.debug("GARAGE IS FULL licence= " + vehicle.getLicense() + " Thread= " + Thread.currentThread().getName());
        }

        return garage;
    }

}
