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
import com.java8.example.garage.task.SensorsLock;
import com.java8.example.garage.utils.SimulationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by miroslavkopecky on 10/09/14.
 *
 *
 * The Simulation starts with generated set of LicensePlates and randomly creates vehicles to them.
 * - There is Used Shared GarageLock to monitor behaviour according to accessed threads
 * - Example is similar to GarageSimulator1Test -> only Sensors are delayed and Locking is observed
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
 */
public class GarageSimulator3Test {

    private static final Logger logger = LoggerFactory.getLogger(GarageSimulator1Test.class);

    private static final int MAX_LEVEL = 3; // configuration fo the Garage
    private static final int MAX_SLOT = 3; //max slots per level
    private static final int SENSORS_IN = 3;
    private static final int SENSORS_OUT = 3;
    private static final int MAX_LABEL = 10;
    private static final int MAX_PER_ENTRANCE = 4;

    public static void main(String... args) throws Exception{
        logger.debug("GARAGE SIMULATOR TWO");

        //Construct Garage Sensor Lock
        SensorsLock sensorsLock = new SensorsLock();

        //Label Service
        LabelService labelService = LabelServiceImpl.getInstance();

        //Garage Service
        GarageService garageService = GarageServiceImpl.getInstance();

        //Vehicle Service
        VehicleService vehicleService = VehicleServiceImpl.getInstance();

        //Manager Service
        GarageManagerService garageManagerService = GarageManagerServiceImpl.getInstance();

        logger.debug("START SIMULATION: CARS ARE COMING");
        int maxFreePlaces = MAX_LEVEL * MAX_SLOT;
        GarageCounter counter = new GarageCounter(maxFreePlaces);
        Garage garage = garageService.create(counter, MAX_LEVEL, MAX_SLOT, SENSORS_IN, SENSORS_OUT, MAX_PER_ENTRANCE);

        //Prepare simulation labels
        Set<String> vehicleLabels = labelService.generateVehicleLabels(MAX_LABEL);
        logger.debug("LABELS = " + vehicleLabels);

        //Get Testing Data
        Vehicle[][] inVehicles = vehicleService.getTestInVehicles(garage, vehicleLabels);

        //Start Simulation with Shared Lock
        Thread[] sensorInThreads = SimulationUtils.startInVehicleSimulation(sensorsLock, garage, inVehicles);

        /*
         * print out lock status
         */
        SimulationUtils.printSensorLockBehaviour(sensorsLock);

        SimulationUtils.stopInVehicleSimulation(sensorInThreads, garage.getSensorsIn());

        garageManagerService.printGarageState(garage);

        //Remove Vehicle with Shared Lock
        SimulationUtils.outVehicleSimulation(sensorsLock, garage, inVehicles);
        vehicleService.printVehicles(garage, inVehicles);

        //GarageManager actions
        logger.debug("START MANAGER SIMULATION");
        garageManagerService.printGarageState(garage);
        //Check status of generated vehicles (inVehicles)
        garageManagerService.printRandomParkedVehicle(garage, inVehicles);
        logger.debug("MANGER garage state= " + (garageManagerService.garageFreeSpace(garage) > 0));


        //Vehicles status
        vehicleService.printVehicles(garage, inVehicles);

        //Restart New Vehicles With Old and New Data
        Vehicle[][] restInVehiclesWithNew = SimulationUtils
                .simulationWithOldCarsAndNew(garage, inVehicles, vehicleLabels);

        Thread[] sensorInThreads2 = SimulationUtils.startInVehicleSimulation(garage, restInVehiclesWithNew);
        SimulationUtils.stopInVehicleSimulation(sensorInThreads2, garage.getSensorsIn());

        //Vehicles status
        vehicleService.printVehicles(garage, inVehicles);

        //GarageManager actions
        logger.debug("RESTART MANAGER SIMULATION");
        garageManagerService.printGarageState(garage);
        logger.debug("RESTART MANAGER SIMULATION FIRST ROUND");
        vehicleService.printVehicles(garage, inVehicles);
        logger.debug("RESTART MANAGER SIMULATION SECOND ROUND");
        vehicleService.printVehicles(garage, restInVehiclesWithNew);
    }


}
