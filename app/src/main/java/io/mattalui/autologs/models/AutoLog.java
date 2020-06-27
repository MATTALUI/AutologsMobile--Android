package io.mattalui.autologs.models;

public class AutoLog {
  public int id;
  public float miles;
  public float fillupAmount;
  public float fillupCost;
  public String note;
  public String location;
  public String coords;
  public String createdAt;
  // Associative IDs
  public int user;
  public int vehicle;

  public String toString(){
    return "Vehicle #" + this.vehicle + ": " + this.miles + " miles";
  }

  public void display() {
    System.out.println("AutoLog {");
    System.out.println("\tid: " + id);
    System.out.println("\tmiles: " + miles);
    System.out.println("\tfillupAmount: " + fillupAmount);
    System.out.println("\tfillupCost: " + fillupCost);
    System.out.println("\tnote: " + note);
    System.out.println("\tlocation: " + location);
    System.out.println("\tcoords: " + coords);
    System.out.println("\tcreatedAt: " + createdAt);
    System.out.println("}");
  }
}
