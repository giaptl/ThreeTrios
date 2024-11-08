package strategy;

import java.util.List;

import model.Card;
import model.Grid;
import model.Player;
import model.ReadOnlyThreeTriosModel;

public class FlipMaximizerStrategy implements Strategy {

  @Override
  public Move selectMove(Player player, ReadOnlyThreeTriosModel model) {
    List<Card> hand = model.getPlayerHand(player);
    Grid grid = model.getGrid();

    Move bestMove = null;
    int maxFlips = -1;

    // Iterate over all cards in hand and all positions on grid
    for (Card card : hand) {
      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getColumns(); col++) {
          if (grid.getCell(row, col).isEmpty()) {
            // Simulate placing this card at this position
            int flips = model.getNumCardsAbleToFlip(player, card, row, col);

            // If this move flips more cards than previous best, choose it
            if (flips > maxFlips || (flips == maxFlips && isUpperLeft(row, col, bestMove))) {
              bestMove = new Move(card, row, col);
              maxFlips = flips;
            }
          }
        }
      }
    }

    return bestMove != null ? bestMove : Move.findFallbackMove(hand, grid);
  }

  private boolean isUpperLeft(int row, int col, Move currentBest) {
    // Break ties by choosing upper-leftmost coordinate
    if (currentBest == null) {
      return true;
    }
    return row < currentBest.getRow() || (row == currentBest.getRow() && col < currentBest.getCol());
  }

}
