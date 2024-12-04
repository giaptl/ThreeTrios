package model.grid;

/**
 * Represents an empty cell on the game grid.
 * This class extends {@link Cell} and always indicates that it is empty.
 */
public class EmptyCell extends Cell {

  /**
   * Overrides the {@link Cell#isEmpty()} method to always return {@code true}.
   *
   * @return {@code true}, indicating that this cell is always empty
   */
  @Override
  public boolean isEmpty() {
    return true;
  }

  /**
   * Overrides the {@link Cell#render()} method to return the symbol representing an empty cell.
   *
   * @return the underscore character {@code "_"} representing an empty cell
   */
  @Override
  public String render() {
    return "_";
  }
}
