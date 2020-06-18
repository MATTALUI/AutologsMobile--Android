package io.mattalui.autologs;

import android.os.Bundle;
import android.widget.EditText;

import io.mattalui.autologs.UserProtectedActivity;
import io.mattalui.autologs.models.Vehicle;

public class VehicleActivity extends UserProtectedActivity {
    protected Vehicle vehicle;
    protected EditText makeInput;
    protected EditText modelInput;
    protected EditText yearInput;
    protected EditText nicknameInput;
    protected EditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFocusContentView(R.layout.activity_vehicle_form);

        makeInput = (EditText)findViewById(R.id.makeInput);
        modelInput = (EditText)findViewById(R.id.modelInput);
        yearInput = (EditText)findViewById(R.id.yearInput);
        nicknameInput = (EditText)findViewById(R.id.nicknameInput);
        descriptionInput = (EditText)findViewById(R.id.descriptionInput);

        fetchVehicle();
    }

    protected void fetchVehicle() {}
}
