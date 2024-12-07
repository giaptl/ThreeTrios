package model;

import extraFeatures.BattleRuleStrategy;

public class PlusBattleRule implements BattleRuleStrategy {
  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    return false;
  }
}
