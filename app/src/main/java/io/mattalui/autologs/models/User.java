package io.mattalui.autologs.models;

public class User {
  public String email;
  public String firstName;
  public String lastName;
  public String nickname;
  public String description;

  public void display() {
    System.out.println("User {");
    System.out.println("\temail: " + email);
    System.out.println("\tfirstName: " + firstName);
    System.out.println("\tlastName: " + lastName);
    System.out.println("\tnickname: " + nickname);
    System.out.println("\tdescription: " + description);
    System.out.println("}");
  }

  public String fullName() {
    return firstName + " " + lastName;
  }
}
