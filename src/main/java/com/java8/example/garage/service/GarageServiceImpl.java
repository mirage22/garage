package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.task.GarageCounter;
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
 * GarageServiceImpl Service is Singleton
 *
 * responsible for the services related to the object Garage
 */
public class GarageServiceImpl implements GarageService {

    //static reference to itself
    private volatile static GarageServiceImpl INSTANCE;

    //Public method to Singleton
    public static GarageServiceImpl getInstance(){
        if(INSTANCE == null){
            synchronized (GarageServiceImpl.class){
                if(INSTANCE == null)
                    INSTANCE = new GarageServiceImpl();
            }
        }
        return INSTANCE;
    }

    private GarageServiceImpl(){

    }

    //Better change to properties


    @Override
    public Garage create(int maxLevel, int maxSlot, int sensorsIn, int sensorsOut, int maxEntrance) {
        return new Garage(maxLevel, maxSlot, sensorsIn, sensorsOut, maxEntrance);
    }

    @Override
    public Garage create(GarageCounter counter, int maxLevel, int maxSlot, int sensorsIn, int sensorsOut, int maxEntrance) {
        return new Garage(counter, maxLevel, maxSlot, sensorsIn, sensorsOut, maxEntrance);
    }

    /**
     * Get Free Parking place from Garage
     * @param garage - specific garage
     * @return
     */
    @Override
    public ParkingPlace getFreeParkingPlace(Garage garage) {
        if(garage.isFree()){

            ParkingPlace[][] parkingPlace = garage.getPlaces();

            for(int i=0; i < garage.getMaxLevel(); i++ ){
                for(int j=0; j < garage.getMaxSlot(); j++){
                    if(parkingPlace[i][j].isFree()){
                        return parkingPlace[i][j];
                    }
                }
            }
        }

        return null;
    }

    /**
     * Services assign ParkingPlace inside the Garage to the specific Vehicle
     * @param garage - specific garage
     * @param place - specific parking place in the garage
     * @param vehicle - specific vehicle
     *
     * @return - returns modified Parking Place
     */
    @Override
    public ParkingPlace setParkingPlace(Garage garage, ParkingPlace place, Vehicle vehicle) {
        place.setVehicle(vehicle);
        garage.setPlace(place);

        place.setFree(false);
        vehicle.setGarage(true);

        return place;
    }

    /**
     * Clear ParkingPlace
     * @param place - specific parking place in the garage
     * @param vehicle - specific vehicle
     * @return
     */
    @Override
    public ParkingPlace clearParkingPlace(ParkingPlace place, Vehicle vehicle) {

        place.setFree(true);
        vehicle.setGarage(false);
        place.setVehicle(null);

        return place;
    }
}
