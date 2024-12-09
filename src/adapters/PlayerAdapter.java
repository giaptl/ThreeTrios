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
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    PlayerAdapter that = (PlayerAdapter) obj;
    return adaptee.equals(that.adaptee);
  }

  @Override
  public int hashCode() {
    return adaptee.hashCode();
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
            .map(card -> new CardAdapter(card)) // Use CardAdapter to convert ICard to CardProvider
            .collect(Collectors.toList());
  }

  @Override
  public void addCard(CardProvider card) {
    adaptee.addCard(((CardAdapter) card).getAdaptee());
  }

  @Override
  public void removeCard(CardProvider card) {
    adaptee.removeCard(((CardAdapter) card).getAdaptee());
  }
}