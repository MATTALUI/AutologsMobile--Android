package io.mattalui.autologs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.services.AutologsServices;

public class CreateLogActivity extends LogActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // vehicleId will be specified if creating a log for a vehicle from the vehicles page
        String vehicleId = getIntent().getStringExtra(getString(R.string.vehicle_id));
        if (vehicleId != null){
            int vehiclePos = 0;
            Vehicle vehicle = State.getState().getVehicle(Integer.parseInt(vehicleId));
            for (int i = 0; i < vehicleInput.getCount(); i++){
                if (vehicleInput.getItemAtPosition(i).toString().equalsIgnoreCase(vehicle.toString())){
                    vehiclePos = i;
                    break;
                }
            }
            vehicleInput.setSelection(vehiclePos);
        }
    }

    public void save(View v){
        String vehicleName = vehicleInput.getSelectedItem().toString();
        for (Vehicle vehicle : State.getState().getVehicles()){
            if(vehicle.toString().equals(vehicleName)){
                log.vehicle = vehicle.id;
                break;
            }
        }

        log.fillupAmount = Float.parseFloat(fillupAmountInput.getText().toString());
        log.fillupCost = Float.parseFloat(fillupCostInput.getText().toString());
        log.miles = Float.parseFloat(milesInput.getText().toString());
        log.note = noteInput.getText().toString();
        log.location = locationInput.getText().toString();

        final AutoLog newAutoLog = log;
        final CreateLogActivity that = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AutoLog createdLog = new AutologsServices(usertoken).createLog(newAutoLog);
                State.getState().addLog(createdLog);
                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast myToast = Toast.makeText(that, "Successfully created log.", Toast.LENGTH_LONG);
                        myToast.show();
                        Intent intent = new Intent(that, ViewLogs.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }
}
