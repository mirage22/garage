package com.java8.example.garage.service;

import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.ParkingPlace;
import com.java8.example.garage.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * GarageManager Service is Singleton
 *
 * allow operation up the existing garage
 *
 */
public class GarageManagerServiceImpl implements GarageManagerService {

    //static reference to itself
    private static GarageManagerServiceImpl INSTANCE = new GarageManagerServiceImpl();

    private static final Logger logger = LoggerFactory.getLogger(GarageManagerServiceImpl.class);

    //Public method to Singleton
    public static GarageManagerServiceImpl getInstance(){
        if(INSTANCE == null){
            synchronized (GarageManagerServiceImpl.class){
                if(INSTANCE == null)
                    INSTANCE = new GarageManagerServiceImpl();
            }
        }
        return INSTANCE;
    }

    private GarageManagerServiceImpl(){

    }

    /**
     * Get free spaces available in the garage
     * @param garage - specific garage
     * @return - number of pleces
     */
    @Override
    public int garageFreeSpace(Garage garage) {
        return garage.getFreeSpaces();
    }

    /**
     * Get Vehicle based on the license that could be in the Garage
     * @param garage - specific garage
     * @param license - Vehicle unique license
     * @return - ParkingPlace where vehicle is located otherwise null
     */
    @Override
    public ParkingPlace getVehicleFromGarage(Garage garage, String license) {
        return garage.getParkingPlaceByLicense(license);
    }

    /**
     * Helper function to print the Garage Status into the console
     * @param garage - specific garage
     */
    @Override
    public void printGarageState(Garage garage) {
        logger.debug("Garage State: maxCapacity = " + garage.getMaxCapacity());
        logger.debug("Garage State: parked cars = " + garage.getCounter().get());

        logger.debug("Garage State: AVAILABLE = " + garage.isFree() + " FREE SPACES= " + garage.getFreeSpaces());
        ParkingPlace[][] parkingPlace = garage.getPlaces();
        for(int i=0; i < garage.getMaxLevel(); i++){
            for(int j=0; j < garage.getMaxSlot(); j++){
                logger.debug("Garage State: ParkPlace [" + i + "]" + "[" + j + "] - " + parkingPlace[i][j].getVehicle());
            }
        }
    }

    /**
     * Print into the console garage status based on the knowledge of the vehicles.
     * Those vehicles maybe in the garage
     *
     * @param garage - specific garage
     * @param possibleVehicles - possibly parked vehicles in the specific garage
     */
    @Override
    public void printRandomParkedVehicle(Garage garage, Vehicle[][] possibleVehicles) {
        Random random = new Random();
        boolean vehicleFound = false;
        while(!vehicleFound){

            int checkedSensor = random.nextInt(garage.getSensorsIn());
            int possibleEnter = random.nextInt(garage.getMaxEntrance());

            Vehicle tmpVehicle = possibleVehicles[checkedSensor][possibleEnter];
            if(tmpVehicle != null && tmpVehicle.isGarage()){
                ParkingPlace parkingPlace = getVehicleFromGarage(garage, tmpVehicle.getLicense());
                logger.debug("RandomParkedVehicle = " + parkingPlace.getVehicle() + " level= " +
                        parkingPlace.getLevel() + " slot= " + parkingPlace.getSlot());
                vehicleFound = true;
            }

        }
    }
}
