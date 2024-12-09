package extrafeatures;

import model.Direction;
import model.ICard;

/**
 * This strategy represents normal battle rules (high > low card).
 */
public class NormalBattleRule implements BattleRuleStrategy {

  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    int cardAValue = cardA.getCardValue(direction);
    int cardBValue = cardB.getCardValue(direction.getOpposite());
    System.out.println("NormalBattleRule: " + cardAValue + " vs " + cardBValue);
    return cardAValue > cardBValue;
  }
}
