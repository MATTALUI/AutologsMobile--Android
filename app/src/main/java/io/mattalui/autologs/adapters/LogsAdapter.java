package io.mattalui.autologs.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.mattalui.autologs.R;
import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.models.State;

public class LogsAdapter extends ArrayAdapter<AutoLog> implements View.OnClickListener {
    private List<AutoLog> logs;
    private Context context;
    SimpleDateFormat formatter;

    public LogsAdapter(List<AutoLog> data, Context _context) {
        super(_context, R.layout.log_list_item, data);
        logs = data;
        context = _context;
        formatter = new SimpleDateFormat("MMM dd yyyy");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//            TODO: I'v added the layout and logic for an expansion/collapse element, but I can't get it to work. Look into this as part of a separate issue.
        View view = convertView;

        if (view == null) {
            LayoutInflater inflator;
            inflator = LayoutInflater.from(context);
            view = inflator.inflate(R.layout.log_list_item, null);
        }

        AutoLog log = getItem(position);

        if (log != null){
            Vehicle vehicle = State.getState().getVehicle(log.vehicle);

//            ConstraintLayout logBasicInfo = view.findViewById(R.id.logBasicInfo);
            TextView vehicleName = view.findViewById(R.id.vehicleName);
            TextView previewText = view.findViewById(R.id.previewText);

//            ConstraintLayout logMoreInfo = view.findViewById(R.id.logMoreInfo);
//
//            TextView dateDisplay = view.findViewById(R.id.dateDisplay);
//            TextView milesDisplay = view.findViewById(R.id.milesDisplay);
//            TextView amountDisplay = view.findViewById(R.id.amountDisplay);
//            TextView costDisplay = view.findViewById(R.id.costDisplay);
//            TextView noteDisplay = view.findViewById(R.id.noteDisplay);
//            TextView locationDisplay = view.findViewById(R.id.locationDisplay);
//
            if (vehicle != null){
                vehicleName.setText(vehicle.toString());
            }else{
                vehicleName.setText("Unknown Vehicle");
            }
            previewText.setText(formatPreview(log));
//
//            if (logMoreInfo != null) {
//                dateDisplay.setText(formatDate(log.createdAt));
//                milesDisplay.setText(log.miles + " MI");
//                amountDisplay.setText(log.fillupAmount + " GA");
//                costDisplay.setText("$" + log.fillupCost);
//                noteDisplay.setText(log.note);
//                locationDisplay.setText(log.location);
//
//                logMoreInfo.setVisibility(View.GONE);
//                logBasicInfo.setOnClickListener(this);
//            }
        }

        return view;
    }

    @Override
    public void onClick(View view) {
//        System.out.println("Calling the toggler");
//        ConstraintLayout logMoreInfo = view.findViewById(R.id.logMoreInfo);
//        int visibility = logMoreInfo.getVisibility();
//        int nextVisibility = visibility == View.VISIBLE ? View.GONE : View.VISIBLE;
//        logMoreInfo.setVisibility(nextVisibility);
    }

    private String formatPreview(AutoLog log){
        String preview = formatDate(log.createdAt) + " • " + log.miles + " Miles • " + log.fillupAmount + "GA • $" + log.fillupCost;

        return preview;
    }

    private String formatDate(String milli){
        try{
            Date date = new Date(Long.parseLong(milli));
            return formatter.format(date);
        }catch(Exception e){
            return "Unknown Date";
        }
    }
}
