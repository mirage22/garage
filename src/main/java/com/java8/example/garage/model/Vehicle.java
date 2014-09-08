package com.java8.example.garage.model;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public interface Vehicle {
    String getLicense();

    boolean isGarage();

    void setGarage(boolean garage);
}
