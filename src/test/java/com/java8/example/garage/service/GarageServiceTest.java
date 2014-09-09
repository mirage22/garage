package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.task.GarageCounter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by miroslavkopecky on 09/09/14.
 */

@RunWith(MockitoJUnitRunner.class)
public class GarageServiceTest {

    @Mock
    GarageCounter counter;

    @Mock
    GarageService garageService;

    int number;

    Garage garage;


    @Before
    public void setUp(){
        garage = new Garage();
        number = 1;
    }

    @Test
    public void testCreateGarageMock(){
        when(garageService.create(counter, number, number, number, number, number)).thenReturn(garage);
    }

}
