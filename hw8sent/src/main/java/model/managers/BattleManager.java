package model.managers;

import model.utils.AttackResult;
import model.cards.Card;

/**
 * The {@code BattleManager} class handles battles between two cards.
 * It compares the attack values of the cards and determines the winner.
 */
public class BattleManager {

  /**
   * Performs a battle between the attacking and defending cards.
   *
   * @param attackingCard the card that initiates the attack
   * @param defendingCard the card being attacked
   * @return an {@code AttackResult} containing the outcome of the battle
   */
  public AttackResult performBattle(Card attackingCard, Card defendingCard) {
    boolean flipped = false;
    String winner = "";

    if (attackingCard.getEast() > defendingCard.getWest()) {
      flipped = true;
      winner = "attacker";
    }

    return new AttackResult(flipped, winner);
  }
}
