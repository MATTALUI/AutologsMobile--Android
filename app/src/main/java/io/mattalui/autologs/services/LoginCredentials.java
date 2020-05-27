package io.mattalui.autologs.services;

public class LoginCredentials {
  String email;
  String password;

  public void display() {
    System.out.println("email: " + email);
    System.out.println("password: " + password);
    System.out.println();
  }
}
