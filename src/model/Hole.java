package model;

import java.util.Objects;

/**
 * Represents a hole on the grid. These are cells are not playable to.
 */
public class Hole implements Cell {

  /**
  * Creates a new hole.
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
  public Player getOwner() {
    return null;
  }

  @Override
  public boolean isEmpty() {
    // Always saying it is not possible to place a card on a hole.
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
