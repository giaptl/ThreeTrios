package extraFeatures;

import model.Direction;
import model.ICard;

public class ReverseBattleRule implements BattleRuleStrategy {

  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    int cardAValue = cardA.getCardValue(direction);
    int cardBValue = cardB.getCardValue(direction.getOpposite());
    System.out.println("ReverseBattleRule: " + cardAValue + " vs " + cardBValue);
    return cardAValue < cardBValue;
  }
}