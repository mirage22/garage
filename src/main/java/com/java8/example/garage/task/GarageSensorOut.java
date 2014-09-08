package com.java8.example.garage.task;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class GarageSensorOut implements Runnable{

    private final Lock garageLock = new ReentrantLock();

    private Garage garage;

    private Vehicle[] vehicles;

    public GarageSensorOut(Garage garage, Vehicle[] vehicles){
        this.garage = garage;
        this.vehicles = vehicles;
    }

    @Override
    public void run() {
        garageLock.lock();
        try{
//            Long duration = (long) (Math.random() * 10000);

            for(int i=0; i<vehicles.length; i++) {
                System.out.println("REMOVE THE CAR = " + vehicles[i] + " Thread= " + Thread.currentThread().getName());
                garage = removeVehicle(garage, vehicles[i]);
            }
//            Thread.sleep(duration);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            garageLock.unlock();
        }

    }

    private Garage removeVehicle(Garage garage, Vehicle vehicle) throws InterruptedException{
        if(garage.getCounter().get() != 0){
            ParkingPlace place = garage.getParkingPlace(vehicle);
            place.setFree(true);
            vehicle.setGarage(false);
            place.setVehicle(null);
            garage.getCounter().vehicleOut();
        }
        return garage;
    }
}
