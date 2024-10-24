package model;

public class Hole extends CardCell {

  public Hole() {
    super(null, null);
  }

  @Override
  public boolean isHole() {
    return true;
  }

}
