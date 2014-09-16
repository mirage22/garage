package com.java8.example.garage.task;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.service.GarageManagerServiceImpl;
import com.java8.example.garage.service.GarageService;
import com.java8.example.garage.service.GarageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
 * The MIT License
 *
 * Copyright 2014 Miroslav Kopecky.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

    public GarageSensorIn(Lock lock, Garage garage, Vehicle[] vehicles){
        this.lock = lock;
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

            /*
             * Used by GarageSimulator3 to monitor locking by threads
             */
            if(lock instanceof SensorsLock){
                TimeUnit.MILLISECONDS.sleep(500);
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
