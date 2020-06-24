package io.mattalui.autologs.models;

import java.util.ArrayList;
import java.util.List;

public class VehicleStats {
    private Vehicle vehicle;
    private List<AutoLog> logs;
    private float averageFillupCost;
    private float averageFillupAmount;
    private float averageMilesPerGallon;

    public VehicleStats(Vehicle _vehicle){
        vehicle = _vehicle;
        logs = new ArrayList<AutoLog>();
        averageFillupCost = -1.0f;
        averageFillupAmount = -1.0f;
        averageMilesPerGallon = -1.0f;
    }

    public void calculate() {
        int totalLogs = logs.size();
        if (totalLogs == 0) { return; }

        float fillupCostTotal = 0.0f;
        float fillupAmountTotal = 0.0f;

        for (AutoLog log : logs) {
            fillupCostTotal += log.fillupCost;
            fillupAmountTotal += log.fillupAmount;
        }

        averageFillupCost = fillupCostTotal / totalLogs;
        averageFillupAmount = fillupAmountTotal / totalLogs;

        // We need to have at least two logs in order to calculate fuel efficiency
        // since it requires getting differences from the previous log
        if (totalLogs > 1){
            float mpgTotal = 0.0f;
            for (int i = 1; i < totalLogs; i++){
                AutoLog prevLog = logs.get(i - 1);
                AutoLog log = logs.get(i);
                float milesTraveled = log.miles - prevLog.miles;
                mpgTotal += milesTraveled / log.fillupAmount;
            }
            averageMilesPerGallon = mpgTotal / (totalLogs - 1);
        }
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public float getAverageFillupCost() { return averageFillupCost; }

    public float getAverageFillupAmount() { return averageFillupAmount; }

    public float getAverageMilesPerGallon() { return averageMilesPerGallon; }

    public void registerLog(AutoLog log){
        logs.add(log);
    }

    public String toString() {
        return "VEHICLE STATS { afc: " + averageFillupCost + ", afa: " + averageFillupAmount + ", ampg: " + averageMilesPerGallon + "}";
    }
}
