package com.java8.example.garage.utils;

import java.util.HashMap;
import java.util.Map;
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
 * Created by miroslavkopecky on 10/09/14.
 *
 * Type of available Vehicles that can be inside the Garage
 *
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
