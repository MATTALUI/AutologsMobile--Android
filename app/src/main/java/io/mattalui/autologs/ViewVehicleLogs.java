package io.mattalui.autologs;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import io.mattalui.autologs.adapters.LogsAdapter;
import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;

public class ViewVehicleLogs extends ViewLogs {
    @Override
    protected void buildContentFromState() {
        final State state = State.getState();
        final ViewVehicleLogs that = this;
        final int vehicleId = Integer.parseInt(getIntent().getStringExtra(getString(R.string.vehicle_id)));
        final Vehicle vehicle = state.getVehicle(vehicleId);
        final int spinnerVisibility = state.isLogsLoaded() ? View.INVISIBLE : View.VISIBLE;
        final int noLogVisibility = state.isLogsLoaded() && state.getLogs().size() == 0 ? View.VISIBLE : View.INVISIBLE;
        final int logsListVisibility = state.isLogsLoaded() && state.getVehicleLogs(vehicleId).size() > 0 ? View.VISIBLE : View.INVISIBLE;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView title = that.findViewById(R.id.title);
                title.setText("Logs for " + vehicle.toString());
                that.spinner.setVisibility(spinnerVisibility);
                that.noLogs.setVisibility(noLogVisibility);
                that.logsView.setVisibility(logsListVisibility);
                that.logsView.setAdapter(new LogsAdapter(state.getVehicleLogs(vehicleId), that));
            }
        });
    }

    @Override
    public void createLog(View v){
        final String vehicleId = getIntent().getStringExtra(getString(R.string.vehicle_id));
        Intent intent = new Intent(this, CreateLogActivity.class);
        intent.putExtra(getString(R.string.vehicle_id), vehicleId);
        startActivity(intent);
    }

}
