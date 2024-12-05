package model;

public interface BattleRuleStrategy {

  boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction);

}
