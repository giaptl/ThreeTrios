package model;

public class SameBattleRule implements BattleRuleStrategy{
  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    return false;
  }
}
