package model;

public class NormalBattleRule implements BattleRuleStrategy {

  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    return cardA.getCardValue(direction) > cardB.getCardValue(direction.getOpposite());
  }
}
