package model;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerActionListener;
import strategy.Move;
import strategy.Strategy;

public class MachinePlayer implements IPlayer {
  private final String name;
  private final List<Card> hand;
  private final List<PlayerActionListener> listeners = new ArrayList<>();
  private final Strategy strategy;

  public MachinePlayer(String name, List<Card> hand, Strategy strategy) {
    this.name = name;
    this.hand = new ArrayList<>(hand);
    this.strategy = strategy;
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

}
