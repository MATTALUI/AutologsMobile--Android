package io.mattalui.autologs.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.mattalui.autologs.BuildConfig;
import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.models.User;

import java.util.List;

public class AutologsServices {

  String usertoken;

  public AutologsServices(){
    this.usertoken = null;
  }

  public AutologsServices(String _token) {
    this.usertoken = _token;
  }

  /////////////////////////////////
  //  URL BUILDERS
  /////////////////////////////////
  private String buildBaseUrl() {
    return BuildConfig.MISCAPI_HOST + "/auto-logs/";
  }

  private String buildLogsUrl() {
    return buildBaseUrl() + "logs/";
  }

  private String buildLogUrl(int logId) {
    return buildLogsUrl() + logId + "/";
  }

  private String buildVehiclesUrl() {
    return buildBaseUrl() + "vehicles/";
  }

  private String buildVehicleUrl(int vehicleId) {
    return buildVehiclesUrl() + vehicleId + "/";
  }

  private String buildMISCAPIUrl() {
    return BuildConfig.MISCAPI_HOST + "/global/";
  }

  private String buildUsersUrl() {
    return buildMISCAPIUrl() + "users/";
  }

  private String buildUserUrl() {
    return buildMISCAPIUrl() + "user/";
  }

  private String buildSessionUrl() {
    return buildUserUrl() + "session/";
  }

  /////////////////////////////////
  //  AUTOLOG SERVICES
  /////////////////////////////////
  public List<AutoLog> getLogs() {
    String data = new QuickHTTP(usertoken).get(buildLogsUrl());
    return new Gson().fromJson(data, new TypeToken<List<AutoLog>>(){}.getType());
  }

  public AutoLog getLog(int logId) {
    String data = new QuickHTTP(usertoken).get(buildLogUrl(logId));
    return new Gson().fromJson(data, AutoLog.class);
  }

  public AutoLog createLog(AutoLog log) {
    String json = new Gson().toJson(log);
    String data = new QuickHTTP(usertoken).post(buildLogsUrl(), json);
    return new Gson().fromJson(data, AutoLog.class);
  }

  public AutoLog updateLog(AutoLog log) {
    String json = new Gson().toJson(log);
    String data = new QuickHTTP(usertoken).put(buildLogUrl(log.id), json);
    return new Gson().fromJson(data, AutoLog.class);
  }

  public AutoLog deleteLog(int logId) {
    String data = new QuickHTTP(usertoken).delete(buildLogUrl(logId));
    return new Gson().fromJson(data, AutoLog.class);
  }

  /////////////////////////////////
  //  VEHICLES SERVICES
  /////////////////////////////////
  public List<Vehicle> getVehicles() {
    String data = new QuickHTTP(usertoken).get(buildVehiclesUrl());
    return new Gson().fromJson(data, new TypeToken<List<Vehicle>>(){}.getType());
  }

  public Vehicle getVehicle(int vehicleId) {
    String data = new QuickHTTP(usertoken).get(buildVehicleUrl(vehicleId));
    return new Gson().fromJson(data, Vehicle.class);
  }

  public Vehicle createVehicle (Vehicle vehicle) {
    String json = new Gson().toJson(vehicle);
    String data = new QuickHTTP(usertoken).post(buildVehiclesUrl(), json);
    return new Gson().fromJson(data, Vehicle.class);
  }

  public Vehicle updateVehicle(Vehicle vehicle) {
    String json = new Gson().toJson(vehicle);
    String data = new QuickHTTP(usertoken).put(buildLogUrl(vehicle.id), json);
    return new Gson().fromJson(data, Vehicle.class);
  }

  public Vehicle deleteVehicle(int vehicleId) {
    String data = new QuickHTTP(usertoken).delete(buildVehicleUrl(vehicleId));
    return new Gson().fromJson(data, Vehicle.class);
  }

  /////////////////////////////////
  //  USER SERVICES
  /////////////////////////////////
  public User whoAmI() {
    String data = new QuickHTTP(usertoken).get(buildUserUrl());
    return new Gson().fromJson(data, User.class);
  }

  public LoginResponse login(LoginCredentials credentials) {
    String json = new Gson().toJson(credentials);
    String data = new QuickHTTP(usertoken).post(buildSessionUrl(), json);
    return new Gson().fromJson(data, LoginResponse.class);
  }

  public LoginResponse signUp(UserCreator newUser) {
    String json = new Gson().toJson(newUser);
    String data = new QuickHTTP(usertoken).post(buildUsersUrl(), json);
    return new Gson().fromJson(data, LoginResponse.class);
  }
}
