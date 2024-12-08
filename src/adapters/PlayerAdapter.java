package adapters;

import finalProviderCode.model.CardProvider;
import finalProviderCode.model.PlayerProvider;
import player.IPlayer;
import model.ICard;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerAdapter implements PlayerProvider {
  private final IPlayer adaptee;

  public PlayerAdapter(IPlayer adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public String getName() {
    return adaptee.getName();
  }

  @Override
  public String getColor() {
    return "";
  }

  @Override
  public List<CardProvider> getHand() {
    return adaptee.getHand().stream()
            .map(card -> (CardProvider) card) // Replace with actual conversion logic
            .collect(Collectors.toList());
  }

  @Override
  public void addCard(CardProvider card) {
    adaptee.addCard((ICard) card);
  }

  @Override
  public void removeCard(CardProvider card) {
    adaptee.removeCard((ICard) card);
  }
}