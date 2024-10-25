package model;

/**
 * Represents a direction on the grid.
 */
public enum Direction {
  NORTH, SOUTH, EAST, WEST;

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
