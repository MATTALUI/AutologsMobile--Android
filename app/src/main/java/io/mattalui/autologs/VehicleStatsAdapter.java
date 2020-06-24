package io.mattalui.autologs;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import io.mattalui.autologs.models.VehicleStats;

public class VehicleStatsAdapter extends ArrayAdapter<VehicleStats> implements View.OnClickListener{
    private List<VehicleStats> stats;
    private Context context;

    public VehicleStatsAdapter(List<VehicleStats> data, Context _context) {
        super(_context, R.layout.stats_list_item, data);
        stats = data;
        context = _context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null) {
            LayoutInflater inflator;
            inflator = LayoutInflater.from(context);
            view = inflator.inflate(R.layout.stats_list_item, null);
        }

        VehicleStats vehicleStat = getItem(position);

        if (vehicleStat != null){
            TextView vehicleName = view.findViewById(R.id.vehicleName);
            TextView averageFillupCost = view.findViewById(R.id.averageFillupCost);
            TextView averageFillupAmount = view.findViewById(R.id.averageFillupAmount);
            TextView averageMPG = view.findViewById(R.id.averageMPG);

            vehicleName.setText(vehicleStat.getVehicle().toString());
            averageFillupCost.setText(formatCost(0.0f));
            averageFillupAmount.setText(formatAmount(0.0f));
            averageMPG.setText(formatEfficiency(0.0f));
        }

        return view;
    }

    private String formatCost(float cost){
        return "$";
    }

    private String formatAmount(float amount) {
        return "GA";
    }

    private String formatEfficiency(float mpg) {
        return "MPG";
    }

    @Override
    public void onClick(View view) {

    }
}
