package io.mattalui.autologs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.mattalui.autologs.adapters.VehiclesAdapter;
import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;

public class ViewVehicles extends UserProtectedActivity {
    ListView vehiclesView;
    ProgressBar spinner;
    TextView noVehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFocusContentView(R.layout.activity_view_vehicles);
        vehiclesView = findViewById(R.id.vehiclesListView);
        spinner = findViewById(R.id.vehicleLoadingSpinner);
        noVehicles = findViewById(R.id.noVehiclesText);
        buildContentFromState();
    }

    public void createVehicle(View v){
        Intent intent = new Intent(this, CreateVehicleActivity.class);
        startActivity(intent);
    }

    @Override
    public void buildContentFromState(){
        final State state = State.getState();
        final ViewVehicles that = this;
        final int spinnerVisibility = state.isVehiclesLoaded() ? View.INVISIBLE : View.VISIBLE;
        final int noVehicleVisibility = state.isVehiclesLoaded() && state.getVehicles().size() == 0 ? View.VISIBLE : View.INVISIBLE;
        final int vehiclesListVisibility = state.isVehiclesLoaded() && state.getVehicles().size() > 0 ? View.VISIBLE : View.INVISIBLE;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                that.spinner.setVisibility(spinnerVisibility);
                that.noVehicles.setVisibility(noVehicleVisibility);
                that.vehiclesView.setVisibility(vehiclesListVisibility);
                that.vehiclesView.setAdapter(new VehiclesAdapter(state.getVehicles(), that));
            }
        });
    }
}
