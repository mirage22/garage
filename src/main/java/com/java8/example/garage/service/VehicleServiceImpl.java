package com.java8.example.garage.service;

import com.java8.example.garage.model.CarVehicle;
import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.MotoVehicle;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.utils.NoVehicleTypeException;
import com.java8.example.garage.utils.VehicleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
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
 * GarageServiceImpl Service is Singleton
 */
public class VehicleServiceImpl implements VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    //Allowed number of Vehicle types -> based on the requirements
    private static final int MAX_VEHICLE_TYPES = 2;


    //static reference to itself
    private volatile static VehicleServiceImpl INSTANCE;

    //Public method to Singleton
    public static VehicleServiceImpl getInstance(){
        if(INSTANCE == null){
            synchronized (VehicleServiceImpl.class){
                if(INSTANCE == null)
                    INSTANCE = new VehicleServiceImpl();
            }
        }
        return INSTANCE;
    }

    private VehicleServiceImpl(){

    }

    /**
     * Generate Random Vehicle for the assigned license (the license plate)
     * @param license
     * @return
     */
    @Override
    public Vehicle getRandomVehicle(String license) {
        Random random = new Random();
        return isCar(random.nextInt(MAX_VEHICLE_TYPES))  ?
                new CarVehicle(license) : new MotoVehicle(license);
    }

    /**
     * Method used to generate specific Vehicles Type
     * @param type
     * @param license
     * @return
     */
    @Override
    public Vehicle getVehicleByType(VehicleType type, String license) {

        switch(type){
            case Car:
                return new CarVehicle(license);
            case Motorbike:
                return new MotoVehicle(license);
            default:
                throw new NoVehicleTypeException("No such Vehicle exists", type.getCode());
        }


    }

    /**
     * Generate testing Vehicles that try to enter the Garage
     * @param garage - specific garage
     * @param labels - unique labels
     * @return
     */
    @Override
    public Vehicle[][] getTestInVehicles(Garage garage, Set<String> labels) {
        Vehicle[][] inVehicles = new Vehicle[garage.getSensorsIn()][garage.getMaxEntrance()];

        Iterator<String> iterator = labels.iterator();
        int j = 0;
        for(int i=0; i<labels.size(); i++){
            String license = iterator.next();

            inVehicles[i % garage.getSensorsIn()][j] = getRandomVehicle(license);
            j++;

            if(((i % garage.getSensorsIn()) != 0 && (j % garage.getMaxEntrance()) == 0) ||
                    ((i % garage.getSensorsIn()) == 0 && (j % garage.getMaxEntrance()) == 0)) {
                j = 0;
            }
        }
        return inVehicles;
    }

    /**
     * Create Vehicles Entry structure based on knowledge of the previous labels
     * @param garage - specific garage
     * @param vehicles - possible vehicles which are not in the garage
     * @param labels - new labels
     * @return
     */
    @Override
    public Vehicle[][] getVehiclesNotInGarage(Garage garage, Vehicle[][] vehicles, Set<String> labels) {

        Vehicle[][] inVehicles = new Vehicle[garage.getSensorsIn()][garage.getMaxEntrance()];

        Iterator<String> labelsIterator = labels.iterator();

        Set<Vehicle> possibleVehicles =  new HashSet<>();
        for(int i=0; i<garage.getMaxSlot(); i++){
            for(int j=0; j<garage.getMaxSlot(); j++){
                if(vehicles[i][j] != null && !vehicles[i][j].isGarage()){
                    possibleVehicles.add(vehicles[i][j]);
                } else {
                    if(labelsIterator.hasNext()){
                        possibleVehicles.add(getRandomVehicle(labelsIterator.next()));
                    }
                }

            }
        }

        Iterator<Vehicle> iterator = possibleVehicles.iterator();
        int j = 0;
        for(int i=0; i < possibleVehicles.size(); i++){
            Vehicle vehicle = iterator.next();

            inVehicles[i % garage.getSensorsIn()][j] = vehicle;
            j++;

            if(((i % garage.getSensorsIn()) != 0 && (j % garage.getMaxEntrance()) == 0) ||
                    ((i % garage.getSensorsIn()) == 0 && (j % garage.getMaxEntrance()) == 0)) {
                j = 0;
            }
        }

        return  inVehicles;
    }

    /**
     * Helper method to print into the console the Vehicles structure status for the specific garage
     * @param garage
     * @param vehicles
     */
    @Override
    public void printVehicles(Garage garage, Vehicle[][] vehicles) {

        for(int i=0; i < garage.getSensorsIn(); i++){
            for(int j=0; j< garage.getMaxEntrance(); j++){
                logger.debug("VEHICLES STRUCTURE [" + i + "]" + "[" + j + "] = " + vehicles[i][j]);
            }
        }
    }

    /**
     * Helper to differentiate the Vehicle type
     * @param type
     * @return
     */
    private boolean isCar(int type){
        return (type % MAX_VEHICLE_TYPES) == 0;
    }
}
