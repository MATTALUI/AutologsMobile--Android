package io.mattalui.autologs.services;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import io.mattalui.autologs.BuildConfig;

public class QuickHTTP {

  private static HttpURLConnection getBaseConnection(String url) throws MalformedURLException, IOException {
    System.out.println(url);
    HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
    connection.setRequestProperty("Authorization", "Basic: " + BuildConfig.LOGS_APP_KEY);
    connection.setRequestProperty("X-Identity", BuildConfig.USERTOKEN);
    connection.setRequestProperty("Content-Type", "application/json; utf-8");

    return connection;
  }

  private static String buildQueryString(HashMap<String, String> paramHash) {
    String params = "?";
    Set<String> keys = paramHash.keySet();
    boolean firstKey = true;
    for (String key : keys) {
      if(!firstKey) {
        params = params + "&";
      }

      String value = URLEncoder.encode(paramHash.get(key));
      params = params +  URLEncoder.encode(key) + "=" + value;
      firstKey = false;
    }

    return params;
  }

  private static String readResponse(HttpURLConnection connection) throws IOException {
    InputStream response = null;
    if(connection.getResponseCode() >= 400){
      response = connection.getErrorStream();
    }else{
      response = connection.getInputStream();
    }

    Scanner scanner = new Scanner(response);

    String resp = scanner.useDelimiter("\\A").next();
    System.out.println(resp);
    return resp;
  }

  private static void writeData(HttpURLConnection connection, String data) throws IOException {
    OutputStream output = connection.getOutputStream();
    byte[] body = data.getBytes("utf-8");
    output.write(body, 0, body.length);
  }

  public static String get(String url){
    try {
      HttpURLConnection connection = getBaseConnection(url);
      return readResponse(connection);
    }catch(Exception e) {
      System.out.println(e);
      return "ERROR";
    }
  }

  public static String get(String url, HashMap<String, String> params){
    String requestUrl = url + buildQueryString(params);
    System.out.println(requestUrl);

    return requestUrl;
  }

  public static String post(String url, String data){
    try {
      HttpURLConnection connection = getBaseConnection(url);
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      writeData(connection, data);

      return readResponse(connection);
    }catch(Exception e) {
      System.out.println(e);
      return "ERROR";
    }
  }

  public static String put(String url, String data){
    try {
      HttpURLConnection connection = getBaseConnection(url);
      connection.setRequestMethod("PUT");
      connection.setDoOutput(true);
      writeData(connection, data);

      return readResponse(connection);
    }catch(Exception e) {
      System.out.println(e);
      return "ERROR";
    }
  }

  public static String delete(String url){
    try {
      HttpURLConnection connection = getBaseConnection(url);
      connection.setRequestMethod("DELETE");

      return readResponse(connection);
    }catch(Exception e) {
      return "ERROR";
    }
  }
}
