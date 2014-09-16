package com.java8.example.garage.model;

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
 * Class Garage represent the object that stores Parking places for vehicles
 *
 */
public class Garage {

    private static final int INIT_GARAGE_SIZE = 1;

    int maxCapacity;

    int maxLevel;

    int maxSlot;

    int sensorsIn;

    int sensorsOut;

    int maxEntrance;

    //Atomic counter provides Garage status
    GarageCounter counter;

    private final  ParkingPlace[][] places;

    /**
     * Constructor for testing purposes without Counter
     */
    public Garage() {
        places = new ParkingPlace[INIT_GARAGE_SIZE][INIT_GARAGE_SIZE];
    }

    /**
     * Constructor without counter
     *
     */
    public Garage(int maxLevel, int maxSlot, int sensorsIn, int sensorsOut, int maxEntrance) {

        this.maxLevel = maxLevel;
        this.maxSlot = maxSlot;
        this.maxCapacity = maxLevel * maxSlot;
        this.sensorsIn = sensorsIn;
        this.sensorsOut = sensorsOut;
        this.maxEntrance = maxEntrance;

        places = new ParkingPlace[maxLevel][maxSlot];

        for(int i=0; i < maxLevel; i++){
            for(int j=0; j< maxSlot; j++){
                places[i][j] = new ParkingPlace(null,i,j);
            }
        }
    }

    /**
     * Constructor with properties initialisation
     * Latter could be added as the properties
     *
     * @param counter - Atomic counter for Garage
     * @param maxLevel - Max Level of the Garage
     * @param maxSlot - Max Slots in each level
     * @param sensorsIn - number of Gages IN
     * @param sensorsOut - number of Gages OUT
     * @param maxEntrance - maximum car that can stay at the entrance
     */
    public Garage(GarageCounter counter, int maxLevel, int maxSlot, int sensorsIn, int sensorsOut, int maxEntrance) {

        this.maxLevel = maxLevel;
        this.maxSlot = maxSlot;
        this.maxCapacity = maxLevel * maxSlot;
        this.sensorsIn = sensorsIn;
        this.sensorsOut = sensorsOut;
        this.maxEntrance = maxEntrance;

        this.counter = counter;

        places = new ParkingPlace[maxLevel][maxSlot];

        for(int i=0; i < maxLevel; i++){
            for(int j=0; j< maxSlot; j++){
                places[i][j] = new ParkingPlace(null,i,j);
            }
        }
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMaxSlot() {
        return maxSlot;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public GarageCounter getCounter() {
        return counter;
    }

    public ParkingPlace[][] getPlaces() {
        return places;
    }

    public void setPlace(ParkingPlace place){
        places[place.getLevel()][place.getSlot()] = place;
    }

    public boolean isFree(){
        return maxCapacity > counter.get();
    }

    public int getFreeSpaces(){
        return maxCapacity - counter.get();
    }

    public int getSensorsIn() {
        return sensorsIn;
    }

    public int getSensorsOut() {
        return sensorsOut;
    }

    public int getMaxEntrance() {
        return maxEntrance;
    }

    /**
     * Get Parking place where is specific vehicle
     * @param vehicle - specific vehicle
     * @return - vehicle parking place
     */
    public  ParkingPlace getParkingPlace(Vehicle vehicle){
        for (int i=0; i < maxLevel; i++){
            for(int j=0; j < maxSlot; j++){
                if(isVehicleParked(places[i][j].getVehicle(), vehicle)){
                    return places[i][j];
                }
            }
        }
        return null;
    }

    public  ParkingPlace getParkingPlaceByLicense(String license){
        for (int i=0; i < maxLevel; i++){
            for(int j=0; j < maxSlot; j++){
                if(isVehicleParked( places[i][j].getVehicle(), license)){
                    return places[i][j];
                }
            }
        }
        return null;
    }

    //Private Methods

    private boolean isVehicleParked(Vehicle parkedVehicle, Vehicle vehicle){
        return parkedVehicle != null && parkedVehicle.equals(vehicle);
    }

    private boolean isVehicleParked(Vehicle vehicle, String licence){
        return (vehicle != null && vehicle.getLicense().equals(licence));
    }

}
