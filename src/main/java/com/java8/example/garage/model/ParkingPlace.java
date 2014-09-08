package com.java8.example.garage.model;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public class ParkingPlace {

    private Vehicle vehicle;

    private Integer level;

    private Integer slot;

    public ParkingPlace() {
    }

    public ParkingPlace(Vehicle vehicle, Integer level, Integer slot) {
        this.vehicle = vehicle;
        this.level = level;
        this.slot = slot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getSlot() {
        return slot;
    }

}
