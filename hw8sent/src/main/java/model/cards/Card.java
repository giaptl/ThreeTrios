package model.cards;

/**
 * Represents a card in the game with directional values and a name.
 * Each card has specific values for the north, south, east, and west directions.
 */
public class Card {
  private final String name;
  private final int north;
  private final int south;
  private final int east;
  private final int west;

  /**
   * Constructs a new Card object.
   *
   * @param name the name of the card
   * @param north the value associated with the north direction
   * @param south the value associated with the south direction
   * @param east the value associated with the east direction
   * @param west the value associated with the west direction
   */
  public Card(String name, int north, int south, int east, int west) {
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
  }


  /**
   * Returns a string representation of the card.
   * Includes the name and directional values in a formatted layout.
   *
   * @return a string representation of the card
   */
  @Override
  public String toString() {
    return name + "\nN:" + north + " S:" + south + " E:" + east + " W:" + west;
  }

  /**
   * Returns the card's details in a single-line formatted string.
   *
   * @return the card's formatted details
   */
  public String getFormattedDetails() {
    return name + " (N:" + north + " S:" + south + " E:" + east + " W:" + west + ")";
  }

  /**
   * Gets the name of the card.
   *
   * @return the name of the card
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the value associated with the north direction.
   *
   * @return the north value
   */
  public int getNorth() {
    return north;
  }

  /**
   * Gets the value associated with the south direction.
   *
   * @return the south value
   */
  public int getSouth() {
    return south;
  }

  /**
   * Gets the value associated with the east direction.
   *
   * @return the east value
   */
  public int getEast() {
    return east;
  }

  /**
   * Gets the value associated with the west direction.
   *
   * @return the west value
   */
  public int getWest() {
    return west;
  }

  /**
   * Gets the card's directional details in a multi-line string format.
   *
   * @return the card's directional details
   */
  public String getDetails() {
    return "N: " + north + " S: " + south + " E: " + east + " W: " + west;
  }

}
