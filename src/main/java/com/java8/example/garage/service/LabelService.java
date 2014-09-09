package com.java8.example.garage.service;

import java.util.Set;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * LabelService responsible for the unique Labels for the Vehicles
 *
 */
public interface LabelService {

    Set<String> generateVehicleLabels(int maxLabel);

    Set<String> getNewLabels(int from, int to, Set<String> usedLabels);
}
