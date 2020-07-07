package io.mattalui.autologs;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.services.AutologsServices;
import io.mattalui.autologs.models.AutoLog;
/**
 *Creates the log from the data collected in LogActivity
 */
public class CreateLogActivity extends LogActivity {
    @Override
    protected void fetchLog() {
        log = new AutoLog();
    }

    public void saveLog(View v){
        System.out.println("Saving");
        String vehicleName = vehicleSpinner.getSelectedItem().toString();
        System.out.println(vehicleName);
        for (Vehicle vehicle : State.getState().getVehicles()){
            if(vehicle.toString().equals(vehicleName)){
                vehicle.display();
                log.vehicle = vehicle.id;
                break;
            }
        }
        //log.vehicle = Integer.parseInt(vehicleSpinner.getSelectedItem().toString());
        log.fillupAmount = Float.parseFloat(fillupAmountInput.getText().toString());
        log.fillupCost = Float.parseFloat(fillupCostInput.getText().toString());
        log.miles = Float.parseFloat(milesInput.getText().toString());
        log.note = noteInput.getText().toString();
        log.location = locationInput.getText().toString();
        System.out.println("Create Log Activity");
        log.display();

        final AutoLog newAutoLog = log;
        final CreateLogActivity that = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                final AutoLog createdLog = new AutologsServices(usertoken).createLog(newAutoLog);

                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast myToast = Toast.makeText(that, "Successfully created " +  createdLog.toString(), Toast.LENGTH_LONG);
                        myToast.show();

                        Intent intent = new Intent(that, ViewLogs.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }

}