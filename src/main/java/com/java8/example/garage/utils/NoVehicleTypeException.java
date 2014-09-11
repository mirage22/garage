package com.java8.example.garage.utils;

/**
 * Created by miroslavkopecky on 11/09/14.
 *
 * Customized RuntimeException when new Vehicle is Added but code is
 * not ready
 */
public class NoVehicleTypeException extends RuntimeException{

    private int type;

    public NoVehicleTypeException() {
        super();
    }

    public NoVehicleTypeException(String message, int type) {
        super(message);
        this.type = type;
    }

    public NoVehicleTypeException(String message, int type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder(super.getMessage());
        builder.append(" for VehicleType= ").append(type);
        return builder.toString();
    }
}
