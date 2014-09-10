package com.java8.example.garage.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miroslavkopecky on 10/09/14.
 */
public enum VehicleType {

    Car(0),
    Motorbike(1);


    private int code;

    private static Map<Integer, VehicleType> codeToVehicleTypeMapping;

    private static void initMapping(){
        codeToVehicleTypeMapping = new HashMap<>();
        for(VehicleType ct: values()){
            codeToVehicleTypeMapping.put(ct.getCode(), ct);
        }
    }

    private VehicleType(int c){
        code = c;
    }

    public int getCode(){
        return code;
    }

    public static VehicleType getVehicleType(int code) {
        if(codeToVehicleTypeMapping == null){
            initMapping();
        }
        return codeToVehicleTypeMapping.get(code);
    }

}
