package io.mattalui.autologs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.models.AutoLog;

public class LogActivity extends UserProtectedActivity {
    protected AutoLog log;
    protected Spinner vehicleInput;
    protected EditText milesInput;
    protected EditText fillupAmountInput;
    protected EditText fillupCostInput;
    protected EditText locationInput;
    protected EditText noteInput;
    protected FloatingActionButton deleteLogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFocusContentView(R.layout.activity_log_form);

        vehicleInput = (Spinner)findViewById(R.id.vehicleInput);
        milesInput = (EditText)findViewById(R.id.milesInput);
        fillupAmountInput = (EditText)findViewById(R.id.fillupAmountInput);
        fillupCostInput = (EditText)findViewById(R.id.fillupCostInput);
        locationInput = (EditText)findViewById(R.id.locationInput);
        noteInput = (EditText)findViewById(R.id.noteInput);
        deleteLogButton = (FloatingActionButton)findViewById(R.id.deleteLogButton);

        List<String> vehicleNames = new ArrayList<>();
        for (Vehicle vehicle : State.getState().getVehicles()){
            vehicleNames.add(vehicle.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, vehicleNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleInput.setAdapter(adapter);

        fetchLog();
    }

    protected void fetchLog() {
        log = new AutoLog();
    }
}
