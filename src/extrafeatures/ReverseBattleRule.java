package extrafeatures;

import model.Direction;
import model.ICard;

/**
 * Represents a strategy for reversing the way cards battle. The card with the lower value wins.
 */
public class ReverseBattleRule implements BattleRuleStrategy {

  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    int cardAValue = cardA.getCardValue(direction);
    int cardBValue = cardB.getCardValue(direction.getOpposite());
    System.out.println("ReverseBattleRule: " + cardAValue + " vs " + cardBValue);
    return cardAValue < cardBValue;
  }
}
