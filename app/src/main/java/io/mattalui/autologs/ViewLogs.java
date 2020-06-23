package io.mattalui.autologs;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.models.State;

public class ViewLogs extends UserProtectedActivity {
    TextView noLogs;
    ListView logsView;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFocusContentView(R.layout.activity_view_logs);

        logsView = findViewById(R.id.logsListView);
        spinner = findViewById(R.id.logLoadingSpinner);
        noLogs = findViewById(R.id.noLogsText);
        buildContentFromState();
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
                that.logsView.setAdapter(new ArrayAdapter<AutoLog>(that, android.R.layout.simple_list_item_1, state.getLogs()));
            }
        });
    }
}
