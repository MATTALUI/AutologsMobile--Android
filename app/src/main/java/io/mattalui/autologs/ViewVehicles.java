package io.mattalui.autologs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.services.AutologsServices;

public class ViewVehicles extends UserProtectedActivity {
    ListView vehiclesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFocusContentView(R.layout.activity_view_vehicles);
        vehiclesView = findViewById(R.id.vehiclesListView);

        fetchVehicles();
    }

    public void fetchVehicles() {
        if(usertoken == null) return;
        final ViewVehicles that = this;
        Thread getVehicles = new Thread(new Runnable(){
            @Override
            public void run(){
                final List<Vehicle> vehicles = new AutologsServices(that.usertoken).getVehicles();
                for(Vehicle vehicle : vehicles) vehicle.display();

                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<Vehicle> logsAdapter = new ArrayAdapter<Vehicle>(that, android.R.layout.simple_list_item_1, vehicles);
                        that.vehiclesView.setAdapter(logsAdapter);
                    }
                });
            }
        });
        getVehicles.start();
    }

    public void createVehicle(View v){
        Intent intent = new Intent(this, CreateVehicleActivity.class);
        startActivity(intent);
    }
}
