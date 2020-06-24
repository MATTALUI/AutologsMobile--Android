package io.mattalui.autologs;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
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
            TextView noLogs = view.findViewById(R.id.noLogsMessage);
            TextView averageFillupCost = view.findViewById(R.id.averageFillupCost);
            TextView averageFillupAmount = view.findViewById(R.id.averageFillupAmount);
            TextView averageMPG = view.findViewById(R.id.averageMPG);

            vehicleName.setText(vehicleStat.getVehicle().toString());
            if (vehicleStat.emptyLogs()){
                noLogs.setVisibility(View.VISIBLE);
                averageFillupCost.setVisibility(View.GONE);
                averageFillupAmount.setVisibility(View.GONE);
                averageMPG.setVisibility(View.GONE);
            }else{
                averageFillupCost.setText(formatCost(vehicleStat.getAverageFillupCost()));
                averageFillupAmount.setText(formatAmount(vehicleStat.getAverageFillupAmount()));
                averageMPG.setText(formatEfficiency(vehicleStat.getAverageMilesPerGallon()));

                if (vehicleStat.emptyMPG()){
                    averageMPG.setVisibility(View.GONE);
                }
            }
        }

        return view;
    }

    private String formatCost(float cost){
        DecimalFormat df = new DecimalFormat("0.00");
        return "Average Fillup Cost: $" + df.format(cost);
    }

    private String formatAmount(float amount) {
        DecimalFormat df = new DecimalFormat("0.00");
        return "Average Fillup Amount: " + df.format(amount) + "GA";
    }

    private String formatEfficiency(float mpg) {
        DecimalFormat df = new DecimalFormat("0.00");
        return "Average Efficiency: " + df.format(mpg) + "MPG";
    }

    @Override
    public void onClick(View view) {

    }
}
