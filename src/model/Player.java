package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player {

  List<Card> hand;
  String name;

  /**
   * Creates a new player with the given name and hand.
   * @param playerName the name of the player
   * @param hand the list of cards in the player's hand
   */
  public Player(String playerName, List<Card> hand) {
    this.name = playerName;
    this.hand = hand;
  }

  /**
   * Gets the name of the player.
   * @return the name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * Returns a mutable copy of the current player's hand at any instance in the game.
   */
  public List<Card> getHand() {
    return new ArrayList<>(this.hand);
  }
}
