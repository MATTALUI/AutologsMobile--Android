package io.mattalui.autologs.models;

public class AutoLog {
  public int id;
  public float miles;
  public float fillupAmount;
  public float fillupCost;
  public String note;
  public String location;
  public String coords;
  // Associative IDs
  public int user;
  public int vehicle;

  public String toString(){
    return "miles: " + this.miles;
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
    System.out.println("}");
  }
}
