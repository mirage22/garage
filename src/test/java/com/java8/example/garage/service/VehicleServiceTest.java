package com.java8.example.garage.service;

import com.java8.example.garage.model.CarVehicle;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.utils.VehicleType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by miroslavkopecky on 10/09/14.
 *
 * Test  VehicleService functionality
 */
public class VehicleServiceTest {

    VehicleService vehicleService;
    String license;

    @Before
    public void setUp(){
        vehicleService = VehicleServiceImpl.getInstance();
        license = "1";
    }

    @Test
    public void testRandomVehicle(){

        Vehicle vehicle = vehicleService.getRandomVehicle(license);

        assertThat(vehicle, is(notNullValue()));
        assertThat("Plate License 1 ", vehicle.getLicense(), is(license));

    }

    @Test
    public void testSpecificVehicleType(){
        Vehicle carVehicle = vehicleService.getVehicleByType(VehicleType.Car, license);

        assertThat(carVehicle, is(notNullValue()));
        assertThat(carVehicle, instanceOf(CarVehicle.class));
        assertThat("Plate License 1 ", carVehicle.getLicense(), is(license));
    }

}
