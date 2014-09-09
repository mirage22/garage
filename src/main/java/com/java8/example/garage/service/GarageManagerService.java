package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * Interface for the GarageManagerService
 */
public interface GarageManagerService {

    int garageFreeSpace(Garage garage);

    ParkingPlace getVehicleFromGarage(Garage garage, String license);

    void printGarageState(Garage garage);

    void printRandomParkedVehicle(Garage garage, Vehicle[][] possibleVehicles);
}
