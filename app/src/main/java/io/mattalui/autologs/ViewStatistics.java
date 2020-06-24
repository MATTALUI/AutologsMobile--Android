package io.mattalui.autologs;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.models.State;

public class ViewStatistics extends UserProtectedActivity {
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFocusContentView(R.layout.activity_view_stats);

        spinner = findViewById(R.id.statLoadingSpinner);
        buildContentFromState();
    }

    @Override
    public void buildContentFromState(){
        final State state = State.getState();
        final ViewStatistics that = this;
        final int spinnerVisibility = state.isStatsLoaded() ? View.INVISIBLE : View.VISIBLE;
        if (state.isLogsLoaded() && state.isVehiclesLoaded() && !state.isStatsLoaded()){
            state.buildStats();
        }

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                that.spinner.setVisibility(spinnerVisibility);
            }
        });
    }
}
