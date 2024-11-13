package strategy;

import java.util.List;

import model.Card;
import model.Grid;
import model.Player;
import model.ReadOnlyThreeTriosModel;

/**
 * Strategy that selects the move which places the card in a corner position if available. If no
 * corners are available, the default move is to select the uppermost-leftmost position on the grid
 * with the card index closest to 0 from the player's hand.
 */
public class CornerStrategy implements Strategy {

  @Override
  public Move selectMove(Player player, ReadOnlyThreeTriosModel model) {
    List<Card> hand = model.getPlayerHand(player);
    Grid grid = model.getGrid();

    // Define corner positions: top-left, top-right, bottom-left, bottom-right
    int[][] corners = {{0, 0}, {0, grid.getColumns() - 1},
            {grid.getRows() - 1, 0}, {grid.getRows() - 1, grid.getColumns() - 1}};

    Move bestMove = null;
    int minOpponentFlips = Integer.MAX_VALUE;

    for (int[] corner : corners) {
      int row = corner[0];
      int col = corner[1];

      // Skip if this corner is a hole or already occupied
      if (!grid.getCell(row, col).isEmpty()) {
        continue;
      }

      for (Card card : hand) {
        // Create a deep copy of the model for simulation

        // Simulate placing this card at this position
        model.getNumCardsAbleToFlip(player, card, row, col);

        // Find the maximum number of flips the opponent could achieve
        int opponentMaxFlips = findMaxFlipsForOpponent(
                model.getOpponent(player),
                model
        );

        // Choose the move that minimizes the opponent's potential flips
        if (opponentMaxFlips < minOpponentFlips ||
                (opponentMaxFlips == minOpponentFlips && isUpperLeft(row, col, bestMove))) {
          bestMove = new Move(card, row, col);
          minOpponentFlips = opponentMaxFlips;
        }
      }
    }

    return bestMove != null ? bestMove : Move.findFallbackMove(hand, grid, model, player);
  }


  private int simulateOpponentBestMove(Card card, int row, int col, Player player, ReadOnlyThreeTriosModel model) {
    // Place this player's card on the simulated grid
//    model.playCard(player, card, row, col);

    // Get opponent player
    Player opponent = model.getOpponent(player);

    // Now simulate all possible moves for the opponent and find their best move
    return findMaxFlipsForOpponent(opponent, model);
  }

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

  private boolean isUpperLeft(int row, int col, Move currentBest) {
    if (currentBest == null) return true;
    return row < currentBest.getRow() || (row == currentBest.getRow() && col < currentBest.getCol());
  }
}
