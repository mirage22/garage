package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
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

    @Before
    public void setUp(){
        garageService = GarageServiceImpl.getInstance();
    }

    @Test
    public void testCreateGarage(){
        Garage garage = garageService.create(2,2, 2, 2, 2);

        assertThat(garage, is(notNullValue()));
        assertThat(garage.getCounter(), is(nullValue()));
        assertThat(garage.getMaxLevel(), is(2));
        assertThat(garage.getMaxSlot(), is(2));

    }

}
