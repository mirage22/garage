package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.task.GarageCounter;

/**
 * Created by miroslavkopecky on 08/09/14.
 *
 * Interface of GarageService
 *
 */
public interface GarageService {

    Garage create(int maxLevel, int maxSlot, int sensorsIn, int sensorsOut, int maxEntrance);

    Garage create(GarageCounter counter, int maxLevel, int maxSlot, int sensorsIn, int sensorsOut, int maxEntrance);

    ParkingPlace getFreeParkingPlace(Garage garage);

    void setParkingPlace(Garage garage, ParkingPlace place, Vehicle vehicle);

}
