package model;

/**
 * Enum that represents a direction on the grid.
 */
public enum Direction {
  NORTH, SOUTH, EAST, WEST;


  /**
   * Gets the row offset for the current direction.
   *
   * @return -1 if the direction is NORTH, 1 if the direction is SOUTH,
   *         and 0 for all other directions.
   */
  public int getRowOffset() {
    switch (this) {
      case NORTH:
        return -1;
      case SOUTH:
        return 1;
      default:
        return 0;
    }
  }

  /**
   * Gets the column offset for the current direction.
   *
   * @return 1 if the direction is EAST, -1 if the direction is WEST,
   *         and 0 for all other directions.
   */
  public int getColOffset() {
    switch (this) {
      case EAST:
        return 1;
      case WEST:
        return -1;
      default:
        return 0;
    }
  }

  /**
   * Gets the opposite direction for the current direction.
   *
   * @return the opposite direction.
   * @throws IllegalArgumentException if the direction is invalid.
   */
  public Direction getOpposite() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }
}
