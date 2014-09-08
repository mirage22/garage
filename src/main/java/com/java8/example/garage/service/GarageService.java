package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.task.GarageCounter;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public interface GarageService {

    Garage create(GarageCounter counter, int maxLevel, int maxSlot);

    ParkingPlace getFreeParkingPlace(Garage garage);

    void setParkingPlace(Garage garage, ParkingPlace place, Vehicle vehicle);

}
