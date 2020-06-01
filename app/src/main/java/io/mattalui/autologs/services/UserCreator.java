package io.mattalui.autologs.services;

import io.mattalui.autologs.models.User;

public class UserCreator extends User {
  public String password;
  public String confirmPassword;

  @Override
  public void display(){
    super.display();
    System.out.println("PASSWORD: " + password);
    System.out.println("CONFIRMATION: " + confirmPassword);
  }
}
