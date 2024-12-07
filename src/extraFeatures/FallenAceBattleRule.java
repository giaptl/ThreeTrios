package extraFeatures;

import model.Direction;
import model.ICard;

public class FallenAceBattleRule implements BattleRuleStrategy {
  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    int cardAValue = cardA.getCardValue(direction);
    int cardBValue = cardB.getCardValue(direction.getOpposite());
    System.out.println("FallenAceBattleRule: " + cardAValue + " vs " + cardBValue);
    if (cardAValue == 10 && cardBValue == 1) {
      return false;
    } else if (cardAValue == 1 && cardBValue == 10) {
      return true;
    }
    return cardAValue > cardBValue;
  }
}
