package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
   * Returns a copy of the current player's hand at any instance in the game.
   */
  public List<Card> getHand() {
    return new ArrayList<>(this.hand);
  }

  public void removeCard(Card card) {
    this.hand.remove(card);
  }

  public void addCard(Card card) {
    this.hand.add(card);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Player player = (Player) obj;
    return Objects.equals(name, player.name) && Objects.equals(hand, player.hand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, hand);
  }
}
