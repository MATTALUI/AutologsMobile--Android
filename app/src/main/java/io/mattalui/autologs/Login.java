package io.mattalui.autologs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import io.mattalui.autologs.services.AutologsServices;
import io.mattalui.autologs.services.LoginCredentials;
import io.mattalui.autologs.services.LoginResponse;

public class Login extends AppCompatActivity {
    EditText emailInput;
    EditText passwordInput;
    Button loginButton;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = (EditText)findViewById(R.id.emailInput);
        passwordInput = (EditText)findViewById(R.id.passwordInput);
        loginButton = (Button)findViewById(R.id.loginButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);
    }

    public void login(View v){
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

        // TODO: We should disable buttons and inputs here while it's checking credentials

        final Login _that = this;
        Thread loginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                LoginCredentials creds = new LoginCredentials(email, password);
                final LoginResponse response = new AutologsServices().login(creds);
                response.display();
                if(response.hasError()){
                    return;
                }

                response.user.display();
                SharedPreferences.Editor prefEditor = _that.getSharedPreferences(getString(R.string.PREFERENCES_USER_DATA), Context.MODE_PRIVATE).edit();
                prefEditor.putString(getString(R.string.CONSTANTS_USERTOKEN), response.userToken);
                prefEditor.putString(getString(R.string.CONSTANTS_USERDATA), new Gson().toJson(response.user));
                prefEditor.commit();

                _that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast myToast = Toast.makeText(_that, "Welcome, " + response.user.fullName(), Toast.LENGTH_LONG);
                        myToast.show();

                        Intent intent = new Intent(_that, ViewLogs.class);
                        startActivity(intent);
                    }
                });

            }
        });

        loginThread.start();
    }

    public void signUp(View v){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
