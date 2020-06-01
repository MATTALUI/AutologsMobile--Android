package io.mattalui.autologs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class UserProtectedActivity extends AppCompatActivity {
    String usertoken;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = this.getSharedPreferences(getString(R.string.PREFERENCES_USER_DATA), Context.MODE_PRIVATE);
        prefEditor = pref.edit();

        validateUserToken();
    }

    public void logout(View v){
        prefEditor.remove(getString(R.string.CONSTANTS_USERTOKEN));
        prefEditor.commit();
        goToLogin();

        Toast myToast = Toast.makeText(this, "Hurry back soon!", Toast.LENGTH_LONG);
        myToast.show();
    }

    private void validateUserToken(){
        usertoken = pref.getString(getString(R.string.CONSTANTS_USERTOKEN), null);
        if (usertoken == null){
            goToLogin();
        }
    }

    private void goToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
