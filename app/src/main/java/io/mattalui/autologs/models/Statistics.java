package io.mattalui.autologs.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public List<VehicleStats> getVehicleStatistics() {
        List<VehicleStats> vs = new ArrayList<VehicleStats>();
        for (VehicleStats stat : data.values()){
            vs.add(stat);
        }
        return vs;
    }

    public void display() {
        for (Integer vehicleId : data.keySet()){
            System.out.println("Vehicle #" + vehicleId + ": " + data.get(vehicleId).toString());
        }
    }
}
