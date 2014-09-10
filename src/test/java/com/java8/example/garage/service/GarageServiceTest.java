package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.MotoVehicle;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.task.GarageCounter;
import com.java8.example.garage.utils.VehicleType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by miroslavkopecky on 10/09/14.
 *
 * Test GarageService functionality
 * Create simple Garage without counter
 *
 */
public class GarageServiceTest {

    GarageService garageService;
    VehicleService vehicleService;

    @Before
    public void setUp(){
        garageService = GarageServiceImpl.getInstance();
        vehicleService = VehicleServiceImpl.getInstance();
    }

    @Test
    public void testCreateGarage(){
        Garage garage = garageService.create(2,2, 2, 2, 2);

        assertThat(garage, is(notNullValue()));
        assertThat(garage.getCounter(), is(nullValue()));
        assertThat(garage.getMaxLevel(), is(2));
        assertThat(garage.getMaxSlot(), is(2));

    }

    @Test
    public void assignParkingPlaceToVehicle(){

        int maxParkingSpaces = 4;

        GarageCounter counter = new GarageCounter(maxParkingSpaces);
        Garage garage = garageService.create(counter, 2,2, 2, 2, 2);
        assertThat(garage, is(notNullValue()));

        ParkingPlace freeParkingPlace = garageService.getFreeParkingPlace(garage);
        assertThat(freeParkingPlace, is(notNullValue()));

        String vehicleLabel = "1";
        Vehicle motoVehicle = vehicleService.getVehicleByType(VehicleType.Motorbike, vehicleLabel);
        assertThat(motoVehicle, instanceOf(MotoVehicle.class));
        assertThat(motoVehicle.getLicense(), is(vehicleLabel));

        //Check parking place status after assignment
        freeParkingPlace = garageService.setParkingPlace(garage, freeParkingPlace, motoVehicle );
        assertTrue(!freeParkingPlace.isFree());
        assertTrue(freeParkingPlace.getVehicle().equals(motoVehicle));

    }

}
