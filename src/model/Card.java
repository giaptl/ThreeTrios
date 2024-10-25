package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a card in the game.
 */
public class Card {

  private final String name;
  private Map<Direction, Integer> attackValues;

  /**
   * Creates a new card with the given name and attack values.
   * @param name the name of the card
   * @param north the attack value for the north direction
   * @param south the attack value for the south direction
   * @param east the attack value for the east direction
   * @param west the attack value for the west direction
   */
  public Card(String name, int north, int south, int east, int west) {
    this.name = name;
    this.attackValues = new HashMap<>();
    this.attackValues.put(Direction.NORTH, north);
    this.attackValues.put(Direction.SOUTH, south);
    this.attackValues.put(Direction.EAST, east);
    this.attackValues.put(Direction.WEST, west);
  }

  public String getName() {
    return name;
  }

  public int getAttackValue(Direction direction) {
    return attackValues.get(direction);
  }
}
