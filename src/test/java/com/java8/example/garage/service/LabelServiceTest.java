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
