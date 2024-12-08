package extraFeatures;

import model.Direction;
import model.ICard;

import static model.CardValues.A;

public class SameBattleRule implements BattleRuleStrategy {
  @Override
  public boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction) {
    // First check regular battle rule
    int attackValue = cardA.getCardValue(direction);
    int defenseValue = cardB.getCardValue(direction.getOpposite());
    System.out.println("SameBattleRule: " + attackValue + " vs " + defenseValue);
    boolean regularBattleWon = attackValue > defenseValue;
    boolean regularBattleLost = attackValue < defenseValue;

//    // Specific check for battle between 1 and A
//    if (attackValue == 1 && defenseValue == A.getValue()) {
//      // Define the outcome for this specific case
//      return false;
//    } else if (attackValue == A.getValue() && defenseValue == 1) {
//      // Define the outcome for this specific case
//      return true;
//    }

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