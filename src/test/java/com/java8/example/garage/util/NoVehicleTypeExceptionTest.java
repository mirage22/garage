package com.java8.example.garage.util;

import com.java8.example.garage.utils.NoVehicleTypeException;
import org.junit.Test;

/**
 * Created by miroslavkopecky on 11/09/14.
 *
 * Custom RuntimeException
 */
public class NoVehicleTypeExceptionTest {

    @Test(expected = NoVehicleTypeException.class)
    public void testExceptionNoDefinedVehicle(){

        int undefinedType = 3;
        throw new NoVehicleTypeException("No such Vehicle exists", undefinedType);

    }

}
