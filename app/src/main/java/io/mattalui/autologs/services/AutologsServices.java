package io.mattalui.autologs.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.mattalui.autologs.BuildConfig;
import io.mattalui.autologs.models.AutoLog;
import io.mattalui.autologs.models.Vehicle;
import io.mattalui.autologs.models.User;

import java.util.List;

public class AutologsServices {

  /////////////////////////////////
  //  URL BUILDERS
  /////////////////////////////////
  private static String buildBaseUrl() {
    return BuildConfig.MISCAPI_HOST + "/auto-logs/";
  }

  private static String buildLogsUrl() {
    return buildBaseUrl() + "logs/";
  }

  private static String buildLogUrl(int logId) {
    return buildLogsUrl() + logId + "/";
  }

  private static String buildVehiclesUrl() {
    return buildBaseUrl() + "vehicles/";
  }

  private static String buildVehicleUrl(int vehicleId) {
    return buildVehiclesUrl() + vehicleId + "/";
  }

  private static String buildMISCAPIUrl() {
    return BuildConfig.MISCAPI_HOST + "/global/";
  }

  private static String buildUsersUrl() {
    return buildMISCAPIUrl() + "users/";
  }

  private static String buildUserUrl() {
    return buildMISCAPIUrl() + "user/";
  }

  private static String buildSessionUrl() {
    return buildUserUrl() + "session/";
  }

  /////////////////////////////////
  //  AUTOLOG SERVICES
  /////////////////////////////////
  public static List<AutoLog> getLogs() {
    String data = QuickHTTP.get(buildLogsUrl());
    return new Gson().fromJson(data, new TypeToken<List<AutoLog>>(){}.getType());
  }

  public static AutoLog getLog(int logId) {
    String data = QuickHTTP.get(buildLogUrl(logId));
    return new Gson().fromJson(data, AutoLog.class);
  }

  public static AutoLog createLog(AutoLog log) {
    String json = new Gson().toJson(log);
    String data = QuickHTTP.post(buildLogsUrl(), json);
    return new Gson().fromJson(data, AutoLog.class);
  }

  public static AutoLog updateLog(AutoLog log) {
    String json = new Gson().toJson(log);
    String data = QuickHTTP.put(buildLogUrl(log.id), json);
    return new Gson().fromJson(data, AutoLog.class);
  }

  public static AutoLog deleteLog(int logId) {
    String data = QuickHTTP.delete(buildLogUrl(logId));
    return new Gson().fromJson(data, AutoLog.class);
  }

  /////////////////////////////////
  //  VEHICLES SERVICES
  /////////////////////////////////
  public static List<Vehicle> getVehicles() {
    String data = QuickHTTP.get(buildVehiclesUrl());
    return new Gson().fromJson(data, new TypeToken<List<Vehicle>>(){}.getType());
  }

  public static Vehicle getVehicle(int vehicleId) {
    String data = QuickHTTP.get(buildVehicleUrl(vehicleId));
    return new Gson().fromJson(data, Vehicle.class);
  }

  public static Vehicle createVehicle (Vehicle vehicle) {
    String json = new Gson().toJson(vehicle);
    String data = QuickHTTP.post(buildVehiclesUrl(), json);
    return new Gson().fromJson(data, Vehicle.class);
  }

  public static Vehicle updateVehicle(Vehicle vehicle) {
    String json = new Gson().toJson(vehicle);
    String data = QuickHTTP.put(buildLogUrl(vehicle.id), json);
    return new Gson().fromJson(data, Vehicle.class);
  }

  public static Vehicle deleteVehicle(int vehicleId) {
    String data = QuickHTTP.delete(buildVehicleUrl(vehicleId));
    return new Gson().fromJson(data, Vehicle.class);
  }

  /////////////////////////////////
  //  USER SERVICES
  /////////////////////////////////
  public static User whoAmI() {
    String data = QuickHTTP.get(buildUserUrl());
    return new Gson().fromJson(data, User.class);
  }

  public static LoginResponse login(LoginCredentials credentials) {
    String json = new Gson().toJson(credentials);
    String data = QuickHTTP.post(buildSessionUrl(), json);
    return new Gson().fromJson(data, LoginResponse.class);
  }

  public static LoginResponse signUp(UserCreator newUser) {
    String json = new Gson().toJson(newUser);
    String data = QuickHTTP.post(buildUsersUrl(), json);
    return new Gson().fromJson(data, LoginResponse.class);
  }
}
