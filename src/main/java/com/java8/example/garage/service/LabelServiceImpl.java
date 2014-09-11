package com.java8.example.garage.service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * GarageServiceImpl Service is Singleton
 *
 * Labels provider
 */
public class LabelServiceImpl implements LabelService {

    //static reference to itself
    private static LabelServiceImpl INSTANCE;

    //Public method to Singleton
    public static LabelServiceImpl getInstance(){
        if(INSTANCE == null){
            synchronized (LabelServiceImpl.class){
                if(INSTANCE == null)
                    INSTANCE = new LabelServiceImpl();
            }
        }
        return INSTANCE;
    }

    private LabelServiceImpl(){

    }

    /**
     * Initial Label Generation
     * Provides Set with iterator interface
     * @param maxLabel - maximum SIGN of the Label
     * @return
     */
    @Override
    public Set<String> generateVehicleLabels(int maxLabel) {
        Set<String> result = new HashSet<String>();

        for(int i=0; i < maxLabel; i++){
            result.add(String.valueOf(i));
        }

        return result;
    }

    /**
     * Provide new unique Labels based on the knowledge of the previous ones
     * @param from - from where should begin
     * @param to - ending of the sign
     * @param usedLabels - already generated labels
     * @return
     */
    @Override
    public Set<String> getNewLabels(int from, int to, Set<String> usedLabels) {
        Set<String> result = new HashSet<String>();

        for(int i= from; i < to; i++){
            if(!usedLabels.contains(String.valueOf(i))){
                result.add(String.valueOf(i));
            }
        }

        return result;
    }
}
