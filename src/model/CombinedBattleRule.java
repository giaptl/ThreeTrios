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
      if (!strategy.shouldFlipCard(cardA, cardB, direction)) {
        return false;
      }
    }
    return true;
  }
}
