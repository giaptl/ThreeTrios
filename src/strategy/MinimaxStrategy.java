package strategy;

import java.util.List;

import model.Card;
import model.Grid;
import model.Player;
import model.ReadOnlyThreeTriosModel;

public class MinimaxStrategy implements Strategy {
  @Override
  public Move selectMove(Player player, ReadOnlyThreeTriosModel model) {
    List<Card> hand = model.getPlayerHand(player);
    Grid grid = model.getGrid();

    Move bestMove = null;
    int minOpponentGain = Integer.MAX_VALUE;

    // Iterate over all cards in hand and all positions on grid
    for (Card card : hand) {
      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getColumns(); col++) {
          if (grid.isCellEmpty(row, col)) {
            // Simulate placing this card at this position
            int opponentBestMoveGain = simulateOpponentBestMove(card, row, col, player, model);

            // Choose the move that minimizes the opponent's best possible gain
            if (opponentBestMoveGain < minOpponentGain ||
                    (opponentBestMoveGain == minOpponentGain && isUpperLeft(row, col, bestMove))) {
              bestMove = new Move(card, row, col);
              minOpponentGain = opponentBestMoveGain;
            }
          }
        }
      }
    }

    return bestMove != null ? bestMove : Move.findFallbackMove(hand, grid, model, player); // Fallback if no valid move found
  }

  /**
   * Simulates placing a card at a given position and calculates how much advantage
   * the opponent can gain in their next move.
   */
  private int simulateOpponentBestMove(Card card, int row, int col, Player player, ReadOnlyThreeTriosModel model) {

    // Place this player's card on the simulated grid
//    model.playCard(player, card, row, col);

    // Get opponent player
    Player opponent = model.getOpponent(player);

    // Now simulate all possible moves for the opponent and find their best move
    return findMaxFlipsForOpponent(opponent, model);
  }

  /**
   * Finds the maximum number of flips an opponent can achieve in their next turn.
   */
  private int findMaxFlipsForOpponent(Player opponent, ReadOnlyThreeTriosModel model) {
    List<Card> hand = model.getPlayerHand(opponent);
    Grid grid = model.getGrid();

    int maxFlips = 0;

    // Iterate over all cards in hand and all positions on grid
    for (Card card : hand) {
      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getColumns(); col++) {
          if (grid.getCell(row, col).isEmpty()) {
            // Simulate placing this card at this position and calculate flips
            int flips = model.getNumCardsAbleToFlip(opponent, card, row, col);

            // Track maximum flips that opponent can achieve
            if (flips > maxFlips) {
              maxFlips = flips;
            }
          }
        }
      }
    }

    return maxFlips;
  }

  /**
   * Break ties by choosing upper-leftmost coordinate.
   */
  private boolean isUpperLeft(int row, int col, Move currentBest) {
    if (currentBest == null) return true;
    return row < currentBest.getRow() || (row == currentBest.getRow() && col < currentBest.getCol());
  }

}
