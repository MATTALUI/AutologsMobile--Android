package io.mattalui.autologs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import io.mattalui.autologs.models.State;
import io.mattalui.autologs.services.AutologsServices;
import io.mattalui.autologs.services.LoginResponse;
import io.mattalui.autologs.services.UserCreator;

public class SignUp extends AppCompatActivity implements PropertyChangeListener {
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText confirmInput;
    ProgressBar spinner;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstNameInput = (EditText)findViewById(R.id.firstNameInput);
        lastNameInput = (EditText)findViewById(R.id.lastNameInput);
        emailInput = (EditText)findViewById(R.id.emailInput);
        passwordInput = (EditText)findViewById(R.id.passwordInput);
        confirmInput = (EditText)findViewById(R.id.confirmInput);
        spinner = (ProgressBar)findViewById(R.id.userLoadingSpinner);
        signupButton = (Button)findViewById(R.id.signupButton);

        State.getState().addPropertyChangeListener(this);
        propertyChange(null);
    }

    @Override public void onStop() {
        super.onStop();
        State.getState().removePropertyChangeListener(this);
    }

    public void propertyChange(PropertyChangeEvent evt){
        // In this Activity, this method is the equivalent of buildContentFromState
        final State state = State.getState();
        final SignUp that = this;
        final int formVisibility = state.isUserLoaded() ? View.VISIBLE : View.INVISIBLE;
        final int spinnerVisibility = state.isUserLoaded() ? View.INVISIBLE : View.VISIBLE;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                that.firstNameInput.setVisibility(formVisibility);
                that.lastNameInput.setVisibility(formVisibility);
                that.emailInput.setVisibility(formVisibility);
                that.passwordInput.setVisibility(formVisibility);
                that.confirmInput.setVisibility(formVisibility);
                that.signupButton.setVisibility(formVisibility);
                that.spinner.setVisibility(spinnerVisibility);
            }
        });
    }

    public void signUp(View v) {
      final UserCreator newUser = buildUserData();
      final SignUp _that = this;
      final State state = State.getState();

      state.setUserLoadingState(false);

      new Thread(new Runnable(){
        @Override
        public void run(){
            // try{ Thread.sleep(7000); }catch(Exception e) {}
            final LoginResponse response = new AutologsServices().signUp(newUser);
            response.display();

            if (!response.hasError()){
                response.user.display();
                SharedPreferences.Editor prefEditor = _that.getSharedPreferences(getString(R.string.PREFERENCES_USER_DATA), Context.MODE_PRIVATE).edit();
                prefEditor.putString(getString(R.string.CONSTANTS_USERTOKEN), response.userToken);
                prefEditor.putString(getString(R.string.CONSTANTS_USERDATA), new Gson().toJson(response.user));
                prefEditor.commit();
            }



            _that.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  String toastMessage = "";

                  if(!response.hasError()) {
                      toastMessage = "Welcome, " + response.user.fullName();

                      Intent intent = new Intent(_that, ViewLogs.class);
                      startActivity(intent);
                  } else {
                      toastMessage = response.error;
                  }

                  Toast myToast = Toast.makeText(_that, toastMessage, Toast.LENGTH_LONG);
                  myToast.show();
                  state.setUserLoadingState(true);
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
