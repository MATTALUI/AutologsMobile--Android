package io.mattalui.autologs;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.services.AutologsServices;

public class ViewLog extends LogActivity{
    @Override
    public void fetchLog(){
        State state = State.getState();
        int logId = Integer.parseInt(getIntent().getStringExtra(getString(R.string.log_id)));

        for (AutoLog _log : state.getLogs()){
            if (_log.id == logId){
                log = _log;
                break;
            }
        }
        milesInput.setText(Float.toString(log.miles));
        fillupAmountInput.setText(Float.toString(log.fillupAmount));
        fillupCostInput.setText(Float.toString(log.fillupCost));
        locationInput.setText(log.location);
        noteInput.setText(log.note);

        Vehicle vehicle = state.getVehicle(log.vehicle);
        int vehiclePos = -1;

        for (int i=0; i < vehicleInput.getCount(); i++){
            if (vehicleInput.getItemAtPosition(i).toString().equalsIgnoreCase(vehicle.toString())){
                vehiclePos = i;
                break;
            }
        }

        vehicleInput.setSelection(vehiclePos);
    }

    public void save(View v) {
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

        final AutoLog updateAutoLog = log;
        final ViewLog that = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AutoLog updatedLog = new AutologsServices(usertoken).updateLog(updateAutoLog);
                State.getState().updateLog(updatedLog);
                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast myToast = Toast.makeText(that, "Successfully updated log.", Toast.LENGTH_LONG);
                        myToast.show();
                        Intent intent = new Intent(that, ViewLogs.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }

}
