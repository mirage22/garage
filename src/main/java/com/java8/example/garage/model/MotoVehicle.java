package com.java8.example.garage.model;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class MotoVehicle implements Vehicle {

    private String license;

    private boolean garage;

    public MotoVehicle() {
    }

    public MotoVehicle(String license) {
        this.license = license;
        this.garage = false;
    }

    public MotoVehicle(String license, boolean garage) {
        this.license = license;
        this.garage = garage;
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
        StringBuilder builder = new StringBuilder(getClass().getSimpleName());
        builder.append(" licence= ").append(license);
        builder.append(" garage= ").append(garage);
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass().equals(obj.getClass())){
            MotoVehicle motoVehicle = (MotoVehicle) obj;
            return (license.equals(motoVehicle.getLicense()));
        }
        return false;

    }
}
