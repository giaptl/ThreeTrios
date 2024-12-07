package extraFeatures;

import model.Direction;
import model.ICard;

public class PlusBattleRule implements BattleRuleStrategy {
  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    // First check regular battle rule
    int attackValue = cardA.getCardValue(direction);
    int defenseValue = cardB.getCardValue(direction.getOpposite());
    if (attackValue > defenseValue) {
      return true;
    }

    // Check for "Plus" rule condition
    Direction opposite = direction.getOpposite();
    int sum1 = cardA.getCardValue(opposite) + cardB.getCardValue(direction);

    // Compare with other adjacent cards' sums
    return checkAdjacentSums(cardA, cardB, sum1);
  }

  private boolean checkAdjacentSums(ICard cardA, ICard cardB, int targetSum) {
    int matchingPairs = 0;
    for (Direction dir : Direction.values()) {
      if (cardA.getCardValue(dir.getOpposite()) +
              cardB.getCardValue(dir) == targetSum) {
        matchingPairs++;
      }
    }
    return matchingPairs >= 2;
  }
}
