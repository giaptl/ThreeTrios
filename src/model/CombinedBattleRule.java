package model;

import java.util.List;

public class CombinedBattleRule implements BattleRuleStrategy {

  private final List<BattleRuleStrategy> strategies;

  public CombinedBattleRule(List<BattleRuleStrategy> strategies) {
    this.strategies = strategies;
  }

  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    for (BattleRuleStrategy strategy : strategies) {
      int cardAValue = cardA.getCardValue(direction);
      int cardBValue = cardB.getCardValue(direction.getOpposite());
      if ((cardAValue == 1 && cardBValue == 10)) {
        System.out.println("ReverseFallenAce: " + cardAValue + " vs " + cardBValue);
        return false;
      } else if (cardAValue == 10 && cardBValue == 1) {
        System.out.println("FallenAceReverse: " + cardAValue + " vs " + cardBValue);
        return true;
      } else if (strategy.shouldFlipCard(cardA, cardB, direction)) {
        return true;
      }
    }
    return false;
  }
}
