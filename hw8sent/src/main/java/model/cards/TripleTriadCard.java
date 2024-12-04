package model.cards;

/**
 * A specialized type of {@link Card} used in the Triple Triad game.
 * This class adds a state to track whether the card was flipped
 * during the current round.
 */
public class TripleTriadCard extends Card {

  /** Indicates if the card was flipped during the current round. */
  private boolean flippedThisRound;

  /**
   * Constructs a new {@link TripleTriadCard} with the specified name and attack values.
   *
   * @param name  the name of the card
   * @param north the attack value for the north edge
   * @param south the attack value for the south edge
   * @param east  the attack value for the east edge
   * @param west  the attack value for the west edge
   */
  public TripleTriadCard(String name, int north, int south, int east, int west) {
    super(name, north, south, east, west);
    this.flippedThisRound = false;
  }

  /**
   * Checks if the card was flipped during the current round.
   *
   * @return {@code true} if the card was flipped during the round, {@code false} otherwise
   */
  public boolean isFlippedThisRound() {
    return flippedThisRound;
  }

  /**
   * Sets the state indicating whether the card was flipped during the current round.
   *
   * @param flippedThisRound {@code true} if the card was flipped, {@code false} otherwise
   */
  public void setFlippedThisRound(boolean flippedThisRound) {
    this.flippedThisRound = flippedThisRound;
  }
}
