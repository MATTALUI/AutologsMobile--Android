package io.mattalui.autologs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.services.AutologsServices;

public class ViewLogs extends AppCompatActivity {
    ListView logsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);
        logsView = (ListView)findViewById(R.id.logsListView);

        final ViewLogs that = this;
        Thread getLogs = new Thread(new Runnable(){
            @Override
            public void run(){
                final List<AutoLog> logs = AutologsServices.getLogs();
                for(AutoLog log : logs) log.display();

                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<AutoLog> logsAdapter = new ArrayAdapter<AutoLog>(that, android.R.layout.simple_list_item_1, logs);
                        that.logsView.setAdapter(logsAdapter);
                    }
                });
            }
        });
        getLogs.start();
    }
}
