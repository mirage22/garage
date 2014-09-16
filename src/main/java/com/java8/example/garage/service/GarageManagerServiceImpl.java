package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
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
 * Created by miroslavkopecky on 09/09/14.
 *
 * GarageManager Service is Singleton
 *
 * allow operation up the existing garage
 *
 */
public class GarageManagerServiceImpl implements GarageManagerService {

    //static reference to itself
    private volatile static GarageManagerServiceImpl INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(GarageManagerServiceImpl.class);

    //Public method to Singleton
    public static GarageManagerServiceImpl getInstance(){
        if(INSTANCE == null){
            synchronized (GarageManagerServiceImpl.class){
                if(INSTANCE == null)
                    INSTANCE = new GarageManagerServiceImpl();
            }
        }
        return INSTANCE;
    }

    private GarageManagerServiceImpl(){

    }

    /**
     * Get free spaces available in the garage
     * @param garage - specific garage
     * @return - number of pleces
     */
    @Override
    public int garageFreeSpace(Garage garage) {
        return garage.getFreeSpaces();
    }

    /**
     * Get Vehicle based on the license that could be in the Garage
     * @param garage - specific garage
     * @param license - Vehicle unique license
     * @return - ParkingPlace where vehicle is located otherwise null
     */
    @Override
    public ParkingPlace getVehicleFromGarage(Garage garage, String license) {
        return garage.getParkingPlaceByLicense(license);
    }

    /**
     * Helper function to print the Garage Status into the console
     * @param garage - specific garage
     */
    @Override
    public void printGarageState(Garage garage) {
        logger.debug("Garage State: maxCapacity = " + garage.getMaxCapacity());
        logger.debug("Garage State: parked cars = " + garage.getCounter().get());

        logger.debug("Garage State: AVAILABLE = " + garage.isFree() + " FREE SPACES= " + garage.getFreeSpaces());
        ParkingPlace[][] parkingPlace = garage.getPlaces();
        for(int i=0; i < garage.getMaxLevel(); i++){
            for(int j=0; j < garage.getMaxSlot(); j++){
                logger.debug("Garage State: ParkPlace [" + i + "]" + "[" + j + "] - " + parkingPlace[i][j].getVehicle());
            }
        }
    }

    /**
     * Print into the console garage status based on the knowledge of the vehicles.
     * Those vehicles maybe in the garage
     *
     * @param garage - specific garage
     * @param possibleVehicles - possibly parked vehicles in the specific garage
     */
    @Override
    public void printRandomParkedVehicle(Garage garage, Vehicle[][] possibleVehicles) {
        Random random = new Random();
        boolean vehicleFound = false;
        while(!vehicleFound){

            int checkedSensor = random.nextInt(garage.getSensorsIn());
            int possibleEnter = random.nextInt(garage.getMaxEntrance());

            Vehicle tmpVehicle = possibleVehicles[checkedSensor][possibleEnter];
            if(tmpVehicle != null && tmpVehicle.isGarage()){
                ParkingPlace parkingPlace = getVehicleFromGarage(garage, tmpVehicle.getLicense());
                logger.debug("RandomParkedVehicle = " + parkingPlace.getVehicle() + " level= " +
                        parkingPlace.getLevel() + " slot= " + parkingPlace.getSlot());
                vehicleFound = true;
            }

        }
    }
}
