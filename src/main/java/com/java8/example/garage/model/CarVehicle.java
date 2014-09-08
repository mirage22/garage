package com.java8.example.garage.model;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class CarVehicle implements Vehicle {

    private String license;

    private boolean garage;

    public CarVehicle() {
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getClass().getName());
        builder.append(" licence= ").append(license);
        builder.append(" garage= ").append(garage);
        return builder.toString();
    }
}
