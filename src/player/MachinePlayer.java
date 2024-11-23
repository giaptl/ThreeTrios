package player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controller.PlayerActionListener;
import model.Card;
import model.ThreeTriosModel;
import strategy.Move;
import strategy.Strategy;


/**
 * Class that represents the logic behind the machine player. Or the AI player, which is the player
 * that makes moves using a passed in strategy.
 */
public class MachinePlayer implements IPlayer {
  private static int machinePlayerCount = 0;
  private final String name;
  private final List<Card> hand;
  private final List<PlayerActionListener> listeners = new ArrayList<>();
  private final Strategy strategy;

  /**
   * Creates a new machine player with the given name, hand, and strategy.
   * @param name the name of the player
   * @param hand the list of cards in the player's hand
   * @param strategy the strategy to use for selecting moves
   * @throws IllegalArgumentException if the player name, hand, or strategy is null
   */
  public MachinePlayer(String name, List<Card> hand, Strategy strategy) {
    if (name == null || hand == null || strategy == null) {
      throw new IllegalArgumentException("Player name, hand, or strategy cannot be null.");
    }
    this.name = generateUniqueName(name);
    this.hand = new ArrayList<>(hand);
    this.strategy = strategy;
  }

  // Helper to generate completely unique names for machine players.
  private String generateUniqueName(String baseName) {
    machinePlayerCount++;
    return baseName + machinePlayerCount;
  }

  @Override
  public void takeTurn(ThreeTriosModel model) {
    Move move = strategy.selectMove(this, model);
    for (PlayerActionListener listener : listeners) {
      listener.onMoveSelected(move);
    }
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removePlayerActionListener(PlayerActionListener listener) {
    listeners.remove(listener);
  }

  @Override
  public void addCard(Card card) {
    this.hand.add(card);
  }

  @Override
  public void removeCard(Card card) {
    this.hand.remove(card);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }

  @Override
  public boolean isComputer() {
    return true;
  }

  @Override
  public Strategy getStrategy() {
    return this.strategy;
  }

  @Override
  public void setHand(List<Card> hand) {
    this.hand.clear();
    this.hand.addAll(hand);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    MachinePlayer player = (MachinePlayer) obj;
    return Objects.equals(name, player.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, hand, strategy);
  }

}
