package com.java8.example.garage.task;

import com.java8.example.garage.model.CarVehicle;
import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.MotoVehicle;
import com.java8.example.garage.model.ParkingPlace;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class GarageSensorIn implements Runnable{

    private final Lock garageLock = new ReentrantLock();

    private Garage garage;

    private String[] licences;

    public GarageSensorIn(Garage garage, String[] licences){
        this.garage = garage;
        this.licences = licences;
    }

    @Override
    public void run() {
        garageLock.lock();
        try{
//            Long duration = (long) (Math.random() * 10000);

            for(String licence: licences){
                System.out.println("PLACE THE CAR = " + licence + " Thread= " + Thread.currentThread().getName());
                garage = placeVehicle(garage, 1, licence);
            }

//            Thread.sleep(duration);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            garageLock.unlock();
        }


    }

    private Garage placeVehicle(Garage garage, int type, String license) throws InterruptedException{
        if(garage.isFree()){
            garage.getCounter().vehicleIn();

            ParkingPlace parkingPlace = garage.getFreeParkingPlace();
            parkingPlace.setFree(false);

            switch(type){
                case 0:
                    parkingPlace.setVehicle(new CarVehicle(license, true));
                    break;
                case 1:
                    parkingPlace.setVehicle(new MotoVehicle(license, true));
                    break;
                default:
                    System.err.println("ERROR TYPE");
            }
            garage.setPlace(parkingPlace);
        } else {
            System.out.println("GARAGE FULL = " + license + " Thread= " + Thread.currentThread().getName());
        }

        return garage;
    }
}
