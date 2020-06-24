package io.mattalui.autologs.models;

import java.util.HashMap;

public class Statistics {
    private HashMap<Integer, VehicleStats> data;

    public Statistics() {
        data = new HashMap<Integer, VehicleStats>();

        for (Vehicle vehicle : State.getState().getVehicles()){
            data.put(vehicle.id, new VehicleStats(vehicle));
        }
        for (AutoLog log : State.getState().getLogs()){
            data.get(log.vehicle).registerLog(log);
        }
        for (VehicleStats vehicleStats : data.values()){
            vehicleStats.calculate();
        }
    }

    public void display() {
        for (Integer vehicleId : data.keySet()){
            System.out.println("Vehicle #" + vehicleId + ": " + data.get(vehicleId).toString());
        }
    }
}
