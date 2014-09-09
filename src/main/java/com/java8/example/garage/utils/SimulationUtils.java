package com.java8.example.garage.utils;

import com.java8.example.garage.handler.SensorExceptionHandler;
import com.java8.example.garage.model.Garage;
import com.java8.example.garage.model.Vehicle;
import com.java8.example.garage.service.LabelService;
import com.java8.example.garage.service.LabelServiceImpl;
import com.java8.example.garage.service.VehicleService;
import com.java8.example.garage.service.VehicleServiceImpl;
import com.java8.example.garage.task.GarageSensorIn;
import com.java8.example.garage.task.GarageSensorOut;

import java.util.Set;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * Utils function used for simulation purposes
 */
public class SimulationUtils {

    /**
     * Create new Vehicles Data Structure together with Car there were already in the Garage
     * Structure is ready for Entry Gates
     * @param garage
     * @param preVehicles
     * @param usedLabels
     * @return
     */
    public static Vehicle[][] simulationWithOldCarsAndNew(Garage garage, Vehicle[][] preVehicles,
                                                           Set<String> usedLabels){
        //Vehicle Service
        VehicleService vehicleService = VehicleServiceImpl.getInstance();
        //LabelService
        LabelService labelService = LabelServiceImpl.getInstance();

        Set<String> newLabels = labelService.getNewLabels(8, 12, usedLabels);

        return vehicleService.getVehiclesNotInGarage(garage, preVehicles, newLabels);
    }

    /**
     * Starte simulation of incoming Vehicles
     * @param garage
     * @param inVehicles
     * @return
     */
    public static Thread[] startInVehicleSimulation(Garage garage, Vehicle[][] inVehicles){
        GarageSensorIn[] sensorIns = new GarageSensorIn[garage.getSensorsIn()];
        Thread[] sensorInThreads = new Thread[garage.getSensorsIn()];
        for(int i=0; i < garage.getSensorsIn(); i++){
            sensorIns[i]= new GarageSensorIn(garage, inVehicles[i]);
            sensorInThreads[i] = new Thread(sensorIns[i]);

            //
            sensorInThreads[i].setUncaughtExceptionHandler(new SensorExceptionHandler());
            sensorInThreads[i].start();
        }
        return sensorInThreads;
    }

    /**
     * Stop simulation of incoming Vehicles into Garage
     * @param sensorInThreads
     * @param inSensors
     * @return
     */
    public static Thread[] stopInVehicleSimulation(Thread[] sensorInThreads, int inSensors){
        try{
            for(int i=0; i < inSensors; i++){
                sensorInThreads[i].join();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return sensorInThreads;
    }

    /**
     * Make the simulation that Vehicles are living Garage
     * @param garage
     * @param inVehicles
     * @return
     */
    public static Thread[] outVehicleSimulation(Garage garage, Vehicle[][] inVehicles){
        GarageSensorOut[] sensorOuts = new GarageSensorOut[garage.getSensorsOut()];
        Thread[] sensorOutThreads = new Thread[garage.getSensorsOut()];

        for(int i=0; i < garage.getSensorsOut() - 1 ; i++){
            sensorOuts[i]= new GarageSensorOut(garage, inVehicles[i]);
            sensorOutThreads[i] = new Thread(sensorOuts[i]);
            sensorOutThreads[i].setUncaughtExceptionHandler(new SensorExceptionHandler());
            sensorOutThreads[i].start();
        }

        try{
            for(int i=0; i < garage.getSensorsOut() - 1; i++){
                sensorOutThreads[i].join();
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return sensorOutThreads;
    }

}
