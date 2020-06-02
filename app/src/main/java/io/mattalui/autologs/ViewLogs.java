package io.mattalui.autologs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.services.AutologsServices;

public class ViewLogs extends UserProtectedActivity {
    ListView logsView;
//this is a comment to pull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);
        logsView = (ListView)findViewById(R.id.logsListView);

        fetchLogs();
    }

    public void fetchLogs(){
        if(usertoken == null) return;
        final ViewLogs that = this;
        Thread getLogs = new Thread(new Runnable(){
            @Override
            public void run(){
                final List<AutoLog> logs = new AutologsServices(that.usertoken).getLogs();
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

    public void logout(View v){
        super.logout(v);
    }
}
