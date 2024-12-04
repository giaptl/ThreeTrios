package model;

import java.util.Map;

/**
 * Represents a card in the game.
 * A card has a name and 4 attack values, one for each direction in the order of north, south, east,
 * and west.
 * Card Structure:
 * - Name: A unique identifier for the card
 * - Attack Values: Four integers (1-10) or 'A' (representing 10) for North, South, East, and West.
 */
public interface ICard {

  /**
   * Returns the name of the card.
   *
   * @return the name of the card
   */
  String getName();

  /**
   * Returns the attack value for the given direction.
   *
   * @param direction the direction to get the attack value for
   * @return integer for that value
   * @throws IllegalArgumentException if the direction is null
   */
  String getAttackValue(Direction direction);

}
