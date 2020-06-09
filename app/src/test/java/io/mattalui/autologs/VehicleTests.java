package io.mattalui.autologs;

import io.mattalui.autologs.models.Vehicle;
import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleTests {

    public Vehicle getTestVehicle(){
        Vehicle testVehicle = new Vehicle();

        testVehicle.nickname = "Cool Car";
        testVehicle.year = "1991";
        testVehicle.make = "Saturn";
        testVehicle.model = "S1";

        return testVehicle;
    }

    @Test
    public void toStringWithNickname() {
        Vehicle testVehicle = getTestVehicle();
        assertEquals("Cool Car", testVehicle.toString());

        testVehicle.nickname = "";
        assertEquals("1991 Saturn S1", testVehicle.toString());

        testVehicle.year = "";
        assertEquals("Saturn S1", testVehicle.toString());
    }
}
