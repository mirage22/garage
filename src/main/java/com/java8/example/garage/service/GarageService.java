package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.task.GarageCounter;

/**
 * Created by miroslavkopecky on 08/09/14.
 */
public interface GarageService {

    Garage create(GarageCounter counter, int maxLevel, int maxSlot);

}
