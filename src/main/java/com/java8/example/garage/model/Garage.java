package com.java8.example.garage.model;

import com.java8.example.garage.task.GarageCounter;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class Garage {

    int maxCapacity;

    int maxLevel;

    int maxSlot;

    GarageCounter counter;

    ParkingPlace[][] places;

    public Garage() {
    }

    public Garage(GarageCounter counter, int maxLevel, int maxSlot) {

        this.maxLevel = maxLevel;
        this.maxSlot = maxSlot;
        this.maxCapacity = maxLevel * maxSlot;
        this.counter = counter;

        places = new ParkingPlace[maxLevel][maxSlot];

        System.out.println("CREATE GARAGE maxLevel= " + maxLevel + " maxSlot= " + maxSlot);

        for(int i=0; i < maxLevel; i++){
            for(int j=0; j< maxSlot; j++){
                System.out.println("CREATE GARAGE i= " + i + " j= " + j);
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

    public  ParkingPlace getParkingPlace(Vehicle vehicle){
        for (int i=0; i < maxLevel; i++){
            for(int j=0; j < maxSlot; j++){
                if(places[i][j].getVehicle().equals(vehicle)){
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

    private boolean isVehicleParked(Vehicle vehicle, String licence){
        return (vehicle != null && vehicle.getLicense().equals(licence));
    }

}
