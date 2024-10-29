package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a card in the game.
 * A card has a name and 4 attack values, one for each direction.
 */
public class Card {

  private final String name;
  private Map<Direction, Integer> attackValues;

  /**
   * Creates a new card with the given name and attack values.
   *
   * @param name the name of the card
   * @param north the attack value for the north direction
   * @param south the attack value for the south direction
   * @param east the attack value for the east direction
   * @param west the attack value for the west direction
   * @throws IllegalArgumentException if the name is null
   * @throws IllegalArgumentException if any attack value is less than 1
   * @throws IllegalArgumentException if any attack value is greater than 10
   */
  public Card(String name, int north, int south, int east, int west) {

    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }

    if (north < 0 || south < 0 || east < 0 || west < 0) {
      throw new IllegalArgumentException("Attack values cannot be less than 1.");
    }

    if (north > 10 || south > 10 || east > 10 || west > 10) {
      throw new IllegalArgumentException("Attack values cannot be greater than 10.");
    }

    this.name = name;
    this.attackValues = new HashMap<>();
    this.attackValues.put(Direction.NORTH, north);
    this.attackValues.put(Direction.SOUTH, south);
    this.attackValues.put(Direction.EAST, east);
    this.attackValues.put(Direction.WEST, west);
  }

  /**
   * Returns the name of the card.
   *
   * @return the name of the card
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the attack value for the given direction.
   *
   * @param direction the direction to get the attack value for
   * @return integer for that value
   * @throws IllegalArgumentException if the direction is null
   */
  public int getAttackValue(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null.");
    }
    return attackValues.get(direction);
  }
}
