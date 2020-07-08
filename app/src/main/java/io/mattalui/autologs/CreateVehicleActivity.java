package io.mattalui.autologs;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.services.AutologsServices;

public class CreateVehicleActivity extends VehicleActivity {
    @Override
    protected void fetchVehicle() {
        vehicle = new Vehicle();
    }

    public void save(View v){
        vehicle.make = makeInput.getText().toString();
        vehicle.model = modelInput.getText().toString();
        vehicle.year = yearInput.getText().toString();
        vehicle.nickname = nicknameInput.getText().toString();
        vehicle.description = descriptionInput.getText().toString();

        final Vehicle newVehicle = vehicle;
        final CreateVehicleActivity that = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Vehicle createdVehicle = new AutologsServices(usertoken).createVehicle(newVehicle);
                State.getState().addVehicle(createdVehicle);

                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast myToast = Toast.makeText(that, "Successfully created " +  createdVehicle.toString(), Toast.LENGTH_LONG);
                        myToast.show();

                        Intent intent = new Intent(that, ViewVehicles.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }
}
