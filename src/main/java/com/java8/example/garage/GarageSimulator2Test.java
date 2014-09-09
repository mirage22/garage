package com.java8.example.garage;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.service.GarageManagerService;
import com.java8.example.garage.service.GarageManagerServiceImpl;
import com.java8.example.garage.service.GarageService;
import com.java8.example.garage.service.GarageServiceImpl;
import com.java8.example.garage.service.LabelService;
import com.java8.example.garage.service.LabelServiceImpl;
import com.java8.example.garage.service.VehicleService;
import com.java8.example.garage.service.VehicleServiceImpl;
import com.java8.example.garage.task.GarageCounter;
import com.java8.example.garage.utils.SimulationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * Simulation that start with generate set of LicensePlates randomly create vehicles to them.
 *
 * 1. Generated Vehicles tries to enter the Garage
 * 2. if Vehicle is successful -> ENTER -> assigned to the Parking Places and Garage Counter is incremented ("GarageCounter: A Vehicle has ENTERED)
 * 3. if Vehicle is not accepted -> Message Generated into the Console (example: "GARAGE IS FULL licence= 4 Thread= Thread-0")
 * 4. ManagerService -> can ask for the specific Vehicle based on its license knowledge (example: "RandomParkedVehicle = MotoVehicle licence= 9 garage= true level= 1 slot= 1")
 * 5. ManagerService -> able to print out GarageStatus (example "Garage State: ...")
 * 6. Vehicle are able to leave the Garage (example: "GarageCounter: A Vehicle has gone OUT", "REMOVE THE CAR = CarVehicle licence= 3 garage= true Thread= Thread-3")
 * 8. Everything is printed into the console
 *
 * IN/OUT process is controlled by the Gates. Every Vehicle must go through.
 * tasks 2,3,6 - are responsible thread base to the customization
 *
 * There are included also tests
 * GarageCounter - important to keep update any time number of the cars inside
 * SensorExceptionHandler - when anything happens inside the Thread need to be processed
 *
 */
public class GarageSimulator2Test {

    private static final Logger logger = LoggerFactory.getLogger(GarageSimulator1Test.class);

    //Garage Customization
    private static final int MAX_LEVEL = 2;
    private static final int MAX_SLOT = 2;
    //Censors IN/OUT
    private static final int SENSORS_IN = 1;
    private static final int SENSORS_OUT = 1;

    //VEHICLE LABELS GENERATION
    private static final int MAX_LABEL = 10;

    //MAX VEHICLE PER ENTRANCE
    private static final int MAX_PER_ENTRANCE = 6;

    public static void main(String... args){
        logger.debug("GARAGE SIMULATOR TWO");

        //Label Service
        LabelService labelService = LabelServiceImpl.getInstance();

        //Garage Service
        GarageService garageService = GarageServiceImpl.getInstance();

        //Vehicle Service
        VehicleService vehicleService = VehicleServiceImpl.getInstance();

        //Manager Service
        GarageManagerService garageManagerService = GarageManagerServiceImpl.getInstance();

        logger.debug("START COMING SIMULATION");

        int maxFreePlaces = MAX_LEVEL * MAX_SLOT;
        GarageCounter counter = new GarageCounter(maxFreePlaces);
        Garage garage = garageService.create(counter, MAX_LEVEL, MAX_SLOT, SENSORS_IN, SENSORS_OUT, MAX_PER_ENTRANCE);

        //Prepare simulation labels
        Set<String> vehicleLabels = labelService.generateVehicleLabels(MAX_LABEL);
        logger.debug("LABELS = " + vehicleLabels);

        //Get Testing Data
        Vehicle[][] inVehicles = vehicleService.getTestInVehicles(garage, vehicleLabels);

        //Start Simulation
        Thread[] sensorInThreads = SimulationUtils.startInVehicleSimulation(garage, inVehicles);
        SimulationUtils.stopInVehicleSimulation(sensorInThreads, garage.getSensorsIn());

        garageManagerService.printGarageState(garage);

        //Remove Vehicle
        SimulationUtils.outVehicleSimulation(garage, inVehicles);
        vehicleService.printVehicles(garage, inVehicles);


        //GarageManager actions
        logger.debug("START MANAGER SIMULATION");
        garageManagerService.printGarageState(garage);
        //Check status of generated vehicles (inVehicles)
        garageManagerService.printRandomParkedVehicle(garage, inVehicles);
        logger.debug("MANGER garage state= " + (garageManagerService.garageFreeSpace(garage) > 0));


        //Vehicles status
        vehicleService.printVehicles(garage, inVehicles);


    }



}
