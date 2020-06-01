package io.mattalui.autologs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.mattalui.autologs.services.AutologsServices;
import io.mattalui.autologs.services.LoginResponse;
import io.mattalui.autologs.services.UserCreator;

public class SignUp extends AppCompatActivity {
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText confirmInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstNameInput = (EditText)findViewById(R.id.firstNameInput);
        lastNameInput = (EditText)findViewById(R.id.lastNameInput);
        emailInput = (EditText)findViewById(R.id.emailInput);
        passwordInput = (EditText)findViewById(R.id.passwordInput);
        confirmInput = (EditText)findViewById(R.id.confirmInput);
    }

    public void signUp(View v) {
      final UserCreator newUser = buildUserData();
      final SignUp _that = this;

      new Thread(new Runnable(){
        @Override
        public void run(){
            final LoginResponse response = new AutologsServices().signUp(newUser);
            response.display();
            if (response.hasError()){
                return;
            }

            response.user.display();
            SharedPreferences.Editor prefEditor = _that.getSharedPreferences(getString(R.string.PREFERENCES_USER_DATA), Context.MODE_PRIVATE).edit();
            prefEditor.putString(getString(R.string.CONSTANTS_USERTOKEN), response.userToken);
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
      }).start();

      newUser.display();
    }

    private UserCreator buildUserData() {
      UserCreator newUser = new UserCreator();

      newUser.firstName = firstNameInput.getText().toString();
      newUser.lastName = lastNameInput.getText().toString();
      newUser.email = emailInput.getText().toString();
      newUser.password = passwordInput.getText().toString();
      newUser.confirmPassword = confirmInput.getText().toString();

      return newUser;
    }
}
