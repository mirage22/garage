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
/*
 * The MIT License
 *
 * Copyright 2014 Miroslav Kopecky.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

    @Test
    public void clearParkingPlace(){
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

        //Remove the Vehicle from the ParkingPlace
        freeParkingPlace= garageService.clearParkingPlace(freeParkingPlace, motoVehicle);
        assertThat(freeParkingPlace.getVehicle(), is(nullValue()));

    }

}
