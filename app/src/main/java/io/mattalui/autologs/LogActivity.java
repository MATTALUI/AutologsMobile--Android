package io.mattalui.autologs;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

//import io.mattalui.autologs.UserProtectedActivity;
import java.util.ArrayList;
import java.util.List;

import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;

/**
 * Collects the user's input to log data
 */
public class LogActivity extends UserProtectedActivity {
    protected AutoLog log;
    protected Spinner vehicleSpinner;
    protected EditText fillupAmountInput;
    protected EditText fillupCostInput;
    protected EditText milesInput;
    protected EditText noteInput;
    protected EditText locationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFocusContentView(R.layout.activity_create_log);
        vehicleSpinner = (Spinner) findViewById(R.id.vehicleSpinner);
        List<String> vehicleNames = new ArrayList<>();
        for (Vehicle vehicle : State.getState().getVehicles()){
            vehicleNames.add(vehicle.toString());
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(LogActivity.this,
                android.R.layout.simple_list_item_1, vehicleNames);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(myAdapter);
        fillupAmountInput = (EditText)findViewById(R.id.fillupAmountInput);
        fillupCostInput = (EditText)findViewById(R.id.fillupCostInput);
        milesInput = (EditText)findViewById(R.id.milesInput);
        noteInput = (EditText)findViewById(R.id.noteInput);
        locationInput = (EditText)findViewById(R.id.locationInput);

        fetchLog();
    }

    protected void fetchLog() {}
}