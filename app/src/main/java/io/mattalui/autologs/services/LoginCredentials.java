package io.mattalui.autologs.services;

public class LoginCredentials {
  String email;
  String password;

  public LoginCredentials(){}
  public LoginCredentials(String _email, String _password){
    email = _email;
    password = _password;
  }

  public void display() {
    System.out.println("email: " + email);
    System.out.println("password: " + password);
    System.out.println();
  }


}
