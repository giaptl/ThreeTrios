package model;

/**
 * Represents a hole on the grid. These are cells are not playable to.
 */
public class Hole extends CardCell {

  /**
   * Creates a new hole.
   */
  public Hole() {
    super(null, null);
  }

  @Override
  public boolean isHole() {
    return true;
  }

}
