package io.mattalui.autologs.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import io.mattalui.autologs.R;
import io.mattalui.autologs.ViewLog;
import io.mattalui.autologs.ViewVehicleLogs;
import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.Vehicle;

public class VehiclesAdapter extends ArrayAdapter<Vehicle>  implements View.OnClickListener {
    private List<Vehicle> vehicles;
    private Context context;
    SimpleDateFormat formatter;

    public VehiclesAdapter(List<Vehicle> data, Context _context) {
        super(_context, R.layout.vehicle_list_item, data);
        vehicles = data;
        context = _context;
        formatter = new SimpleDateFormat("MMM dd yyyy");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflator;
            inflator = LayoutInflater.from(context);
            view = inflator.inflate(R.layout.vehicle_list_item, null);
        }

        Vehicle vehicle = getItem(position);

        if (vehicle != null){
            TextView vehicleName = view.findViewById(R.id.vehicleName);
            TextView logCount = view.findViewById(R.id.logCount);

            int count = State.getState().countLogs(vehicle.id);
            String countSuffix = count == 1 ? "" : "s";

            vehicleName.setText(vehicle.toString());
            logCount.setText(count + " Total Log" + countSuffix);

            view.setTag(R.string.vehicle_id, Integer.toString(vehicle.id));
            view.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ViewVehicleLogs.class);
        intent.putExtra(getContext().getString(R.string.vehicle_id), view.getTag(R.string.vehicle_id).toString());
        getContext().startActivity(intent);
    }
}
