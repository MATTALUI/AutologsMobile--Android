package io.mattalui.autologs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import io.mattalui.autologs.models.State;
import io.mattalui.autologs.adapters.LogsAdapter;

public class ViewLogs extends UserProtectedActivity {
    TextView noLogs;
    ListView logsView;
    ProgressBar spinner;
    FloatingActionButton addLogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFocusContentView(R.layout.activity_view_logs);

        logsView = findViewById(R.id.logsListView);
        spinner = findViewById(R.id.logLoadingSpinner);
        noLogs = findViewById(R.id.noLogsText);
        addLogButton = findViewById(R.id.addLogButton);
        buildContentFromState();
    }

    public void createLog(View v){
        Intent intent = new Intent(this, CreateLogActivity.class);
        startActivity(intent);
    }

    @Override
    protected void buildContentFromState() {
        final State state = State.getState();
        final ViewLogs that = this;
        final int spinnerVisibility = state.isLogsLoaded() ? View.INVISIBLE : View.VISIBLE;
        final int noLogVisibility = state.isLogsLoaded() && state.getLogs().size() == 0 ? View.VISIBLE : View.INVISIBLE;
        final int logsListVisibility = state.isLogsLoaded() && state.getLogs().size() > 0 ? View.VISIBLE : View.INVISIBLE;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                that.spinner.setVisibility(spinnerVisibility);
                that.noLogs.setVisibility(noLogVisibility);
                that.logsView.setVisibility(logsListVisibility);
                if (state.getVehicles().size() > 0) {
                    that.addLogButton.show();
                } else {
                    that.addLogButton.hide();
                }
                that.logsView.setAdapter(new LogsAdapter(state.getLogs(), that));
            }
        });
    }
}
