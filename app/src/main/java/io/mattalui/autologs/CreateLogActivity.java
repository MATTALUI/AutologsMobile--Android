package io.mattalui.autologs;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.services.AutologsServices;

/**
 *Creates the log from the data collected in LogActivity
 */
public class CreateLogActivity extends LogActivity {
    @Override
    protected void fetchLog() {
        log = new AutoLog();
    }

    public void save(View v){
        log.fillupAmount = fillupAmountInput.getText().toString();
        log.fillupCost = fillupCostInput.getText().toString();
        log.miles = milesInput.getText().toString();
        log.note = noteInput.getText().toString();
        log.location = locationInput.getText().toString();

        final Log newAutoLog = log;
        final CreateLogActivity that = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Log createdLog = new AutologsServices(usertoken).createLog(newAutoLog);

                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast myToast = Toast.makeText(that, "Successfully created " +  createdLog.toString(), Toast.LENGTH_LONG);
                        myToast.show();

                        Intent intent = new Intent(that, ViewLogs.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }
}