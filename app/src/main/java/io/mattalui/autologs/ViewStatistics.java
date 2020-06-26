package io.mattalui.autologs;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import io.mattalui.autologs.models.State;

public class ViewStatistics extends UserProtectedActivity {
    ProgressBar spinner;
    ListView statsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFocusContentView(R.layout.activity_view_stats);

        spinner = findViewById(R.id.statLoadingSpinner);
        statsList = findViewById(R.id.statsList);

        buildContentFromState();
    }

    @Override
    public void buildContentFromState(){
        final State state = State.getState();
        final ViewStatistics that = this;
        final int spinnerVisibility = state.isStatsLoaded() ? View.INVISIBLE : View.VISIBLE;
        final int listVisibility = state.isStatsLoaded() ? View.VISIBLE : View.INVISIBLE;
        if (state.isLogsLoaded() && state.isVehiclesLoaded() && !state.isStatsLoaded()){
            state.buildStats();
        }

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (state.isStatsLoaded()){
                    VehicleStatsAdapter statsAdapter = new VehicleStatsAdapter(state.getStatistics().getVehicleStatistics(), that);
                    statsList.setAdapter(statsAdapter);
                }

                that.spinner.setVisibility(spinnerVisibility);
                that.statsList.setVisibility(listVisibility);
            }
        });
    }
}
