package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import controller.PlayerActionListener;

/**
 * Represents a player in the game.
 */
public class HumanPlayer implements IPlayer {

  private final List<Card> hand;
  private final String name;
  private final List<PlayerActionListener> listeners = new ArrayList<>();

  /**
   * Creates a new player with the given name and hand.
   * @param playerName the name of the player
   * @param hand the list of cards in the player's hand
   */
  public HumanPlayer(String playerName, List<Card> hand) {
    this.name = playerName;
    this.hand = hand;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<Card> getHand() {
    return new ArrayList<>(this.hand);
  }

  @Override
  public void removeCard(Card card) {
    this.hand.remove(card);
  }

  @Override
  public void addCard(Card card) {
    this.hand.add(card);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    HumanPlayer player = (HumanPlayer) obj;
    return Objects.equals(name, player.name) && Objects.equals(hand, player.hand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, hand);
  }

  @Override
  public void takeTurn(ThreeTriosModel model) {
    // DO NOTHING SINCE HUMAN PLAYERS ACT THROUGH THE VIEW!
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removePlayerActionListener(PlayerActionListener listener) {
    listeners.remove(listener);
  }
}