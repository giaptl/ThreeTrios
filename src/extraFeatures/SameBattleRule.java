package extraFeatures;

import model.Direction;
import model.ICard;

public class SameBattleRule implements BattleRuleStrategy {
  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    // First check regular battle rule
    int attackValue = cardA.getCardValue(direction);
    int defenseValue = cardB.getCardValue(direction.getOpposite());
    boolean regularBattleWon = attackValue > defenseValue;
    boolean regularBattleLost = attackValue < defenseValue;

    // Check for "Same" rule condition
    int sameCount = 0;
    for (Direction dir : Direction.values()) {
      ICard adjacentCard = cardA.getAdjacentCard(dir);
      if (adjacentCard != null) {
        int cardAValue = cardA.getCardValue(dir);
        int adjacentCardValue = adjacentCard.getCardValue(dir.getOpposite());
        if (cardAValue == adjacentCardValue) {
          sameCount++;
        }
      }
    }

    // Flip the card if the regular battle is won or if the "Same" rule condition is met and the battle is not lost
    return (regularBattleWon || sameCount >= 2) && !regularBattleLost;
  }
}