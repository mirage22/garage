package com.java8.example.garage.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
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
 * Label Service test for generating unique labels
 */
public class LabelServiceTest {

    private static final int MAX_LABELS = 5;

    LabelService labelService;
    Set<String> someLabels;


    @Before
    public void setUp(){
        labelService = LabelServiceImpl.getInstance();
        someLabels = new HashSet<>();
        for(int i=0; i < MAX_LABELS; i++){
            someLabels.add(String.valueOf(i));
        }


    }

    @Test
    public void testUniqueLabelsGeneration(){

        Set<String> generatedLabels = labelService.generateVehicleLabels(MAX_LABELS);
        assertThat(generatedLabels, is(notNullValue()));
        assertThat(generatedLabels, is(someLabels));

    }

    @Ignore
    @Test
    public void testExistingElements(){
        Set<String> generatedLabels = labelService.generateVehicleLabels(MAX_LABELS);
        assertThat(generatedLabels, is(notNullValue()));

        String testLabel = "1";
        List<String> generatedLabelsArray = new ArrayList<>(generatedLabels);
        assertThat(generatedLabelsArray, hasItem(testLabel));

        List<String> knownLabelsArray = new ArrayList<>(someLabels);
        assertThat(generatedLabelsArray, is(knownLabelsArray));

        assertTrue(generatedLabelsArray.containsAll(knownLabelsArray));

    }

    @Test
    public void testAddingExistingElement(){
        Set<String> generatedLabels = labelService.generateVehicleLabels(MAX_LABELS);
        assertThat(generatedLabels, is(notNullValue()));

        String existingLabel = "1";
        assertTrue(generatedLabels.contains(existingLabel));

        assertTrue(generatedLabels.size() == MAX_LABELS);
        generatedLabels.add(existingLabel);
        assertTrue(generatedLabels.size() == MAX_LABELS);

    }
}
