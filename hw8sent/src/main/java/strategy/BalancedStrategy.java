package strategy;

import model.ThreeTriosModel;
import model.cards.Card;
import players.Player;

/**
 * Represents a balanced strategy for the Three Trios game.
 * This strategy aims to maximize the number of flips when placing a card.
 */
public class BalancedStrategy implements Strategy {
  private final ThreeTriosModel model;

  /**
   * Constructs a BalancedStrategy with the specified game model.
   *
   * @param model the game model used to evaluate potential moves
   */
  public BalancedStrategy(ThreeTriosModel model) {
    this.model = model;
  }

  /**
   * Executes the strategy for the specified player.
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

    // Iterate over all cells in the grid
    for (int row = 0; row < model.getGridRows(); row++) {
      for (int col = 0; col < model.getGridCols(); col++) {
        // Check if the cell is empty and valid for a move
        if (!model.isCellOccupied(row, col)) {
          // Iterate over each card in the player's hand
          for (Card card : model.getPlayerHand(player)) {
            int flips = model.getPotentialFlips(player, card, row, col);

            // Check if this move is better than the current best move
            if (flips > maxFlips) {
              maxFlips = flips;
              bestMove = new Move(row, col, card);
            }
          }
        }
      }
    }

    // Return the best move found, or null if no valid moves are found
    return bestMove;
  }

  @Override
  public void executeStrategy() {
    // This method is not used in the current implementation but is required by the interface.
    // If needed in the future, implement logic here.
  }
}
