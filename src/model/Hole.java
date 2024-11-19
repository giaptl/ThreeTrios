package model;

import java.util.Objects;

/**
 * Represents a hole on the grid. These are cells are not playable to.
 */
public class Hole implements Cell {

  /**
  * Creates a new hole. Empty constructor since nothing needs to be initialized.
  * Will remove if deemed redundant.
  */
  public Hole() {
    //Empty constructor
  }

  @Override
  public boolean isHole() {
    return true;
  }

  @Override
  public Card getCard() {
    return null; // Holes do not have cards
  }

  @Override
  public IPlayer getOwner() {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Hole;
  }

  @Override
  public int hashCode() {
    return Objects.hash("Hole");
  }
}
