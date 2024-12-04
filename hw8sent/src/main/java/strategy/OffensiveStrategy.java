package strategy;

import model.ThreeTriosModel;
import model.cards.Card;
import players.Player;

/**
 * Represents an offensive strategy for the Three Trios game.
 * This strategy focuses on maximizing the number of flips a player can achieve
 * during their move.
 */
public class OffensiveStrategy implements Strategy {
  private final ThreeTriosModel model;

  /**
   * Constructs an OffensiveStrategy with the specified game model.
   *
   * @param model the game model used to evaluate potential moves
   */
  public OffensiveStrategy(ThreeTriosModel model) {
    this.model = model;
  }

  /**
   * Executes the offensive strategy for the specified player.
   * The strategy iterates through all possible moves and selects the one
   * that maximizes the number of card flips.
   *
   * @param player the player for whom the strategy is being executed
   * @return the best move determined by the strategy, or null if no valid moves exist
   */
  @Override
  public Move executeStrategy(Player player) {
    int maxFlips = 0;
    Move bestMove = null;

    for (int row = 0; row < model.getGridRows(); row++) {
      for (int col = 0; col < model.getGridCols(); col++) {
        if (!model.isCellOccupied(row, col)) {
          for (Card card : model.getPlayerHand(player)) {
            int flips = model.getPotentialFlips(player, card, row, col);

            if (flips > maxFlips) {
              maxFlips = flips;
              bestMove = new Move(row, col, card);
            }
          }
        }
      }
    }

    return bestMove;
  }

  /**
   * Placeholder method for executing a default strategy.
   * This method is not used in the current implementation but is required by the interface.
   * Implement this method if needed in the future.
   */
  @Override
  public void executeStrategy() {
    // This method is intentionally left empty as a placeholder.
  }
}
