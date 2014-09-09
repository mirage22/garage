package com.java8.example.garage.model;

/**
 * Created by miroslavkopecky on 08/09/14.
 *
 * Parking place DataStructure holds information about its status
 * and position inside the Garage
 *
 */
public class ParkingPlace {

    private Vehicle vehicle;

    private Integer level;

    private Integer slot;

    private boolean free;

    public ParkingPlace() {
    }

    /**
     * Construct Parking place based on properties
     * @param vehicle
     * @param level
     * @param slot
     */
    public ParkingPlace(Vehicle vehicle, Integer level, Integer slot) {
        this.vehicle = vehicle;
        this.level = level;
        this.slot = slot;
        this.free = true;
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

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
