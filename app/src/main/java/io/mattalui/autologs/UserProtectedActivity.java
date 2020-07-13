package io.mattalui.autologs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import io.mattalui.autologs.models.State;
import io.mattalui.autologs.models.User;

public class UserProtectedActivity extends AppCompatActivity implements PropertyChangeListener, NavigationView.OnNavigationItemSelectedListener {
    User currentUser;
    String usertoken;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.with_navigation);

        pref = this.getSharedPreferences(getString(R.string.PREFERENCES_USER_DATA), Context.MODE_PRIVATE);
        prefEditor = pref.edit();

        validateUserToken();
        getCurrentUser();
        buildDrawer();
        State.getState(usertoken).addPropertyChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override public void onStop() {
        super.onStop();
        State.getState().removePropertyChangeListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Intent intent = new Intent(this, ViewLogs.class);;
        switch(item.getItemId()) {
            case R.id.auto_logs_link:
                break;
            case R.id.vehicles_link:
                intent = new Intent(this, ViewVehicles.class);
                break;
            case R.id.reports_link:
                intent = new Intent(this, ViewStatistics.class);
                break;
            case R.id.logout_link:
                logout(null);
                return true;
        }
        startActivity(intent);

        return true;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        buildContentFromState();
    }

    protected void buildContentFromState() {}

    protected void setFocusContentView(int viewRef) {
        FrameLayout container = findViewById(R.id.fragment_container);
        View child = getLayoutInflater().inflate(viewRef, null);
        container.addView(child);
    }

    public void logout(View v){
        prefEditor.remove(getString(R.string.CONSTANTS_USERTOKEN));
        prefEditor.commit();
        goToLogin();
        State.getState().logout();

        Toast myToast = Toast.makeText(this, "Hurry back soon!", Toast.LENGTH_LONG);
        myToast.show();
    }

    private void validateUserToken(){
        usertoken = pref.getString(getString(R.string.CONSTANTS_USERTOKEN), null);
        if (usertoken == null){
            goToLogin();
        }
    }

    private void getCurrentUser() {
        if (usertoken == null) {
            return;
        }
        String marshaledUserData  = pref.getString(getString(R.string.CONSTANTS_USERDATA), "{}");
        currentUser = new Gson().fromJson(marshaledUserData, User.class);
    }

    private void buildDrawer() {
        drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        if (currentUser != null){
            View header = navView.getHeaderView(0);

            TextView drawerName = header.findViewById(R.id.drawer_name_display);
            TextView drawerEmail = header.findViewById(R.id.drawer_email_display);

            drawerName.setText(currentUser.fullName());
            drawerEmail.setText(currentUser.email);
        }
    }

    private void goToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
