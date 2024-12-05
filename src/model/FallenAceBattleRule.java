package model;

public class FallenAceBattleRule implements BattleRuleStrategy {
  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    int cardAValue = cardA.getCardValue(direction);
    int cardBValue = cardB.getCardValue(direction.getOpposite());
    return (cardAValue == 1 && cardBValue == 10) || cardAValue > cardBValue;
  }
}
