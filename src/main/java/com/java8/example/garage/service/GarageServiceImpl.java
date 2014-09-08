package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.task.GarageCounter;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class GarageServiceImpl implements GarageService {
    @Override
    public Garage create(GarageCounter counter, int maxLevel, int maxSlot) {
        return new Garage(counter, maxLevel, maxSlot);
    }

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

    @Override
    public void setParkingPlace(Garage garage, ParkingPlace place, Vehicle vehicle) {
        System.out.println("SetParkingPlace for Vehicle = " + vehicle);
        place.setVehicle(vehicle);
        garage.setPlace(place);
    }
}
