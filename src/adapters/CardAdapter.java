package adapters;

import finalProviderCode.model.CardProvider;
import model.Direction;
import model.ICard;

public class CardAdapter implements CardProvider {
  private final ICard adaptee;

  public CardAdapter(ICard adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public int getNorth() {
    return adaptee.getCardValue(Direction.NORTH);
  }

  @Override
  public int getEast() {
    return adaptee.getCardValue(Direction.EAST);
  }

  @Override
  public int getSouth() {
    return adaptee.getCardValue(Direction.SOUTH);
  }

  @Override
  public int getWest() {
    return adaptee.getCardValue(Direction.WEST);
  }

  @Override
  public String getInfo() {
    return adaptee.toString();
  }
}