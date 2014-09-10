package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.utils.VehicleType;

import java.util.Set;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * Operation directly related to the Vehicle
 *
 */
public interface VehicleService {

    Vehicle getRandomVehicle(String license);

    Vehicle getVehicleByType(VehicleType type, String license);

    Vehicle[][] getTestInVehicles(Garage garage, Set<String> labels);

    Vehicle[][] getVehiclesNotInGarage(Garage garage, Vehicle[][] vehicles, Set<String> labels);

    void printVehicles(Garage garage, Vehicle[][] vehicles);
}
