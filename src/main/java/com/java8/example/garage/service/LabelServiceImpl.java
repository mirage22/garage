package com.java8.example.garage.service;

import java.util.HashSet;
import java.util.Set;
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
 * GarageServiceImpl Service is Singleton
 *
 * Labels provider
 */
public class LabelServiceImpl implements LabelService {

    //static reference to itself
    private volatile static LabelServiceImpl INSTANCE;

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
