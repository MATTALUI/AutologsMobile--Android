package io.mattalui.autologs.services;


import io.mattalui.autologs.models.User;

public class LoginResponse {
  public User user;
  public String userToken;
  public String error;

  public void display() {
    System.out.println("LoginResponse {");
    if (hasError()){
      System.out.println("error: " + error);
    } else {
      user.display();
      System.out.println("token: " + userToken);
    }
    System.out.println("}");
  }

  public boolean hasError() {
    return userToken == null;
  }
}
