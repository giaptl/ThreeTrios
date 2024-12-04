package model.utils;

/**
 * The {@code AttackResult} class represents the result of an attack or battle
 * between two cards. It encapsulates whether a card was flipped and who the winner
 * of the battle was.
 */
public class AttackResult {
  private final boolean cardFlipped;
  private final String winner;

  /**
   * Constructs an {@code AttackResult} with the specified outcome of the battle.
   *
   * @param cardFlipped {@code true} if the defending card was flipped, {@code false} otherwise
   * @param winner the winner of the battle, typically "attacker" or "defender"
   */
  public AttackResult(boolean cardFlipped, String winner) {
    this.cardFlipped = cardFlipped;
    this.winner = winner;
  }

  /**
   * Returns whether the defending card was flipped during the battle.
   *
   * @return {@code true} if the card was flipped, {@code false} otherwise
   */
  public boolean isCardFlipped() {
    return cardFlipped;
  }

  /**
   * Returns the winner of the battle.
   *
   * @return the winner of the battle as a {@code String} ("attacker" or "defender")
   */
  public String getWinner() {
    return winner;
  }
}
