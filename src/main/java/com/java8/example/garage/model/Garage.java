package com.java8.example.garage.model;

import com.java8.example.garage.task.GarageCounter;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class Garage {

    int maxCapacity;

    GarageCounter counter;

    ParkingPlace[][] places;

    public Garage() {
    }

    public Garage(GarageCounter counter, int maxLevel, int maxSlot) {
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public GarageCounter getCounter() {
        return counter;
    }

    public ParkingPlace[][] getPlaces() {
        return places;
    }

}
