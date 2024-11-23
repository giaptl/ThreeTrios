package player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controller.PlayerActionListener;
import model.Card;
import model.ThreeTriosModel;
import strategy.Strategy;

/**
 * Represents a player in the game.
 */
public class HumanPlayer implements IPlayer {

  private final List<Card> hand;
  private final String name;
  private final List<PlayerActionListener> listeners = new ArrayList<>();
  private static int humanPlayerCount = 0;

  /**
   * Creates a new player with the given name and hand.
   *
   * @param playerName the name of the player
   * @param hand the list of cards in the player's hand
   * @throws IllegalArgumentException if the player name or hand is null
   */
  public HumanPlayer(String playerName, List<Card> hand) {
    if (playerName == null || hand == null) {
      throw new IllegalArgumentException("Player name or hand cannot be null.");
    }
    this.name = generateUniqueName(playerName);
    this.hand = hand;
  }

  // Much needed helper in order to generate unique names if multiple human players are created.
  private String generateUniqueName(String baseName) {
    humanPlayerCount++;
    return baseName + humanPlayerCount;
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
  public boolean isComputer() {
    return false;
  }

  @Override
  public Strategy getStrategy() {
    return null;
  }

  @Override
  public void setHand(List<Card> hand) {
    this.hand.clear();
    this.hand.addAll(hand);
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