package player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controller.PlayerActionListener;
import model.Card;
import model.ThreeTriosModel;
import strategy.Move;
import strategy.Strategy;

public class MachinePlayer implements IPlayer {
  private static int machinePlayerCount = 0;
  private final String name;
  private final List<Card> hand;
  private final List<PlayerActionListener> listeners = new ArrayList<>();
  private final Strategy strategy;

  public MachinePlayer(String name, List<Card> hand, Strategy strategy) {
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
//    System.out.println("Card: " + move.getCard().getName() + ", Row: " + move.getRow() + ", Col: " + move.getCol());
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

}
