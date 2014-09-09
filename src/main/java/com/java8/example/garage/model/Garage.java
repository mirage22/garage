package com.java8.example.garage.model;

import com.java8.example.garage.task.GarageCounter;

/**
 * Created by miroslavkopecky on 08/09/14.
 *
 * Class Garage represent the object that stores Parking places for vehicles
 *
 */
public class Garage {

    int maxCapacity;

    int maxLevel;

    int maxSlot;

    int sensorsIn;

    int sensorsOut;

    int maxEntrance;

    //Atomic counter provides Garage status
    GarageCounter counter;

    ParkingPlace[][] places;

    public Garage() {
    }

    /**
     * Creator with properties initialisation
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
     * Get Any Free Parking Place
     * @return - return ParkingPlace if it's availble
     */
    public  ParkingPlace getFreeParkingPlace(){

        for(int i=0; i < maxLevel; i++ ){
            for(int j=0; j < maxSlot; j++){
                if(places[i][j].isFree()){
                    return places[i][j];
                }
            }
        }

        return null;
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
