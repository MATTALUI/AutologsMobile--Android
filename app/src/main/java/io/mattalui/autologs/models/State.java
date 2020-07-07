package io.mattalui.autologs.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import io.mattalui.autologs.services.AutologsServices;

public class State {

    private static State instance = null;

    private String userToken;
    private List<AutoLog> logs;
    private List<Vehicle> vehicles;
    private Statistics stats;
    private boolean loadedLogs;
    private boolean loadedVehicles;
    private boolean loadedStats;
    private PropertyChangeSupport support;

    private State() {
        // Instantiate this data just so we don't get errors
        logs = new ArrayList<AutoLog>();
        vehicles = new ArrayList<Vehicle>();
        stats = null;
        loadedVehicles = false;
        loadedLogs = false;
        loadedStats = false;
        support = new PropertyChangeSupport(this);

        refresh();
    }

    public static State getState() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    public static State getState(String _userToken) {
        State instance = getState();
        instance.setToken(_userToken);
        instance.refresh();;
        return instance;
    }

    public void refresh(){
        fetchLogs();
        fetchVehicles();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setToken(String _userToken){
        userToken = _userToken;
    }


    /////////////////////////////////
    //  LOGS
    /////////////////////////////////
    public void fetchLogs(){
        if (loadedLogs || userToken == null) return;
        final State that = this;
        new Thread(new Runnable(){
            @Override
            public void run(){
//                try {Thread.sleep(7000);} catch(Exception e){}
                List<AutoLog> logs = new AutologsServices(userToken).getLogs();
                for (AutoLog log : logs) log.display();
                that.setLogs(logs);
                that.setLogLoadingState(true);
            }
        }).start();
    }

    public List<AutoLog> getLogs() {
        return logs;
    }

    public void setLogs(List<AutoLog> _logs){
        List<AutoLog> prevState = logs;
        logs = _logs;
        support.firePropertyChange("logs", prevState, logs);
    }

    public void addLog(AutoLog log){
        List<AutoLog> prevState = logs;
        logs.add(log);
        support.firePropertyChange("logs", prevState, logs);
    }

    public boolean isLogsLoaded () {
        return loadedLogs;
    }

    public void setLogLoadingState(boolean loadingState) {
        boolean prevState = loadedLogs;
        loadedLogs = loadingState;
        support.firePropertyChange("loadedLogs", prevState, loadedLogs);
    }

    public int countLogs(int vehicleId){
        int total = 0;

        for (AutoLog log : logs){
            if (log.vehicle == vehicleId) {
                total++;
            }
        }

        return total;
    }


    /////////////////////////////////
    //  VEHICLES
    /////////////////////////////////
    private void fetchVehicles() {
        if (loadedVehicles || userToken == null) return;
        final State that = this;
        new Thread(new Runnable(){
            @Override
            public void run(){
//                try {Thread.sleep(7000);} catch(Exception e){}
                List<Vehicle> vehicles = new AutologsServices(userToken).getVehicles();
                for (Vehicle vehicle : vehicles) vehicle.display();
                that.setVehicles(vehicles);
                that.setVehicleLoadingState(true);
            }
        }).start();
    }

    public List<Vehicle> getVehicles(){
        return vehicles;
    }

    public Vehicle getVehicle(int vehicleId) {
        for (Vehicle vehicle : vehicles){
            if (vehicle.id == vehicleId){
                return vehicle;
            }
        }

        return null;
    }

    public void setVehicles(List<Vehicle>_vehicles) {
        List<Vehicle> prevState = vehicles;
        vehicles = _vehicles;
        support.firePropertyChange("vehicles", prevState, vehicles);
    }

    public boolean isVehiclesLoaded() {
        return loadedVehicles;
    }

    public void setVehicleLoadingState(boolean loadingState) {
        boolean prevState = loadedVehicles;
        loadedVehicles = loadingState;
        support.firePropertyChange("loadedVehicles", prevState, loadedVehicles);
    }


    /////////////////////////////////
    //  STATS
    /////////////////////////////////
    public void buildStats() {
        final State that = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try{ Thread.sleep(7000); }catch(Exception e) {}
                stats = new Statistics();
                stats.display();
                that.setStatsLoadingState(true);
            }
        }).start();
    }

    public Statistics getStatistics(){
        return stats;
    }

    public boolean isStatsLoaded() {
        return loadedStats;
    }

    public void setStatsLoadingState(boolean loadingState) {
        boolean prevState = loadedStats;
        loadedStats = loadingState;
        support.firePropertyChange("loadedStats", prevState, loadedStats);
    }
}
