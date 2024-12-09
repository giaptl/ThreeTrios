package extrafeatures;

import model.Direction;
import model.ICard;

/**
 * This strategy 2 adjacent cards' sums to determine the winner.
 */
public class PlusBattleRule implements BattleRuleStrategy {
  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    // First check regular battle rule
    int attackValue = cardA.getCardValue(direction);
    int defenseValue = cardB.getCardValue(direction.getOpposite());

    System.out.println("PlusBattleRule: " + attackValue + " vs " + defenseValue);
    if (attackValue > defenseValue) {
      return true;
    }

    // Check for "Plus" rule condition
    Direction opposite = direction.getOpposite();
    int sum1 = cardA.getCardValue(direction) + cardB.getCardValue(opposite);

    // Compare with other adjacent cards' sums
    return checkAdjacentSums(cardA, cardB, sum1);
  }

  private boolean checkAdjacentSums(ICard cardA, ICard cardB, int targetSum) {
    int matchingPairs = 0;
    for (Direction dir : Direction.values()) {
      ICard adjacentCard = cardA.getAdjacentCard(dir);
      if (adjacentCard != null && cardA.getCardValue(dir) + adjacentCard.getCardValue(
              dir.getOpposite()) == targetSum) {
        matchingPairs++;
      }
    }
    return matchingPairs >= 2;
  }
}
