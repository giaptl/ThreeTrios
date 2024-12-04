package strategy;

import model.ThreeTriosModel;
import model.cards.Card;
import players.Player;

/**
 * Represents a defensive strategy for the Three Trios game.
 * This strategy focuses on minimizing the number of flips an opponent can make in response
 * to the player's move.
 */
public class DefensiveStrategy implements Strategy {
  private final ThreeTriosModel model;

  /**
   * Constructs a DefensiveStrategy with the specified game model.
   *
   * @param model the game model used to evaluate potential moves
   */
  public DefensiveStrategy(ThreeTriosModel model) {
    this.model = model;
  }

  /**
   * Executes the defensive strategy for the specified player.
   * The strategy iterates through all possible moves and selects the one
   * that minimizes the opponent's potential flips.
   *
   * @param player the player for whom the strategy is being executed
   * @return the safest move determined by the strategy, or null if no valid moves exist
   */
  @Override
  public Move executeStrategy(Player player) {
    Move safestMove = null;
    int minOpponentFlips = Integer.MAX_VALUE;

    for (int row = 0; row < model.getGridRows(); row++) {
      for (int col = 0; col < model.getGridCols(); col++) {
        if (!model.isCellOccupied(row, col)) {
          for (Card card : model.getPlayerHand(player)) {
            int opponentFlips = model.getPotentialOpponentFlips(player, card, row, col);

            if (opponentFlips < minOpponentFlips) {
              minOpponentFlips = opponentFlips;
              safestMove = new Move(row, col, card);
            }
          }
        }
      }
    }

    return safestMove;
  }

  @Override
  public void executeStrategy() {
    // This method is not used in the current implementation but is required by the interface.
    // If needed in the future, implement logic here.
  }
}
