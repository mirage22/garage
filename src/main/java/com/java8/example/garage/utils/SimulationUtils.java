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
import com.java8.example.garage.task.SensorsLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
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
 * Created by miroslavkopecky on 09/09/14.
 *
 * Utils function used for simulation purposes
 */
public class SimulationUtils {

    private static final Logger logger = LoggerFactory.getLogger(SimulationUtils.class);

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
     * Start simulation of incoming Vehicles
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
     * Start simulation of incoming Vehicles
     * @param lock - shared lock
     * @param garage
     * @param inVehicles
     * @return
     */
    public static Thread[] startInVehicleSimulation(Lock lock, Garage garage, Vehicle[][] inVehicles){
        GarageSensorIn[] sensorIns = new GarageSensorIn[garage.getSensorsIn()];
        Thread[] sensorInThreads = new Thread[garage.getSensorsIn()];
        for(int i=0; i < garage.getSensorsIn(); i++){
            sensorIns[i]= new GarageSensorIn(lock, garage, inVehicles[i]);
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

    /**
     * Make the simulation that Vehicles are living Garage
     * @param lock - shared lock
     * @param garage
     * @param inVehicles
     * @return
     */
    public static Thread[] outVehicleSimulation(Lock lock, Garage garage, Vehicle[][] inVehicles){
        GarageSensorOut[] sensorOuts = new GarageSensorOut[garage.getSensorsOut()];
        Thread[] sensorOutThreads = new Thread[garage.getSensorsOut()];

        for(int i=0; i < garage.getSensorsOut() - 1 ; i++){
            sensorOuts[i]= new GarageSensorOut(lock, garage, inVehicles[i]);
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

    public static void printSensorLockBehaviour(SensorsLock sensorsLock) throws InterruptedException{
        for (int i=0; i<3; i++) {
			/*
			 * Write info about the lock
			 */
            logger.debug("SensorLOCK: Logging the Lock");
            logger.debug("************************");
            logger.debug("SensorLOCK: Owner= " + sensorsLock.getOwnerName());
            logger.debug("SensorLOCK: Queued Threads= " + sensorsLock.hasQueuedThreads());
            if (sensorsLock.hasQueuedThreads()){
                logger.debug("SensorLOCK: Queue Length= " + sensorsLock.getQueueLength());
                logger.debug("SensorLOCK: Queued Threads: ");
                Collection<Thread> lockedThreads= sensorsLock.getThreads();
                for (Thread lockedThread : lockedThreads) {
                    logger.debug("SensorLOCK: Queued Threads ThreadName= " + lockedThread.getName());
                }
            }
            logger.debug("SensorLOCK: Fairness= " +  sensorsLock.isFair());
            logger.debug("SensorLOCK: Locked:= " +  sensorsLock.isLocked());
            logger.debug("************************");
			/*
			 * Sleep the thread for one second
			 */
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

}
