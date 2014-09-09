package com.java8.example.garage.model;

/**
 * Created by miroslavkopecky on 08/09/14.
 *
 * Vehicles is helper interface to allow flexibility
 *
 */
public interface Vehicle {
    String getLicense();

    boolean isGarage();

    void setGarage(boolean garage);
}
