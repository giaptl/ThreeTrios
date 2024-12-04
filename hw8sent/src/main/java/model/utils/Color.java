package model.utils;

/**
 * The {@code Color} enum represents the two possible player colors in the game.
 * It provides a utility method to get the symbol associated with each color.
 */
public enum Color {
  RED, BLUE;

  /**
   * Returns the symbol associated with the color.
   *
   * @return "R" for RED and "B" for BLUE
   */
  public String getSymbol() {
    return this == RED ? "R" : "B";
  }
}
