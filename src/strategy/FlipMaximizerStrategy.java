package strategy;

import java.util.List;
import java.util.Objects;

import model.Grid;
import model.ICard;
import model.ReadOnlyThreeTriosModel;
import player.IPlayer;

/**
 * Strategy that selects the move which maximizes the number of opponents' cards flipped. If there
 * are 2 moves that have the same number of maximum cards flipped, choose the move that places the
 * card in the upper-leftmost position on the grid. Similarly, if there are 2 cards that have the
 * same number of maximum cards flipped, choose the card that is closest to index 0 in the player's
 * hand.
 */
public class FlipMaximizerStrategy implements Strategy {

  @Override
  public Move selectMove(IPlayer player, ReadOnlyThreeTriosModel model) {
    List<ICard> hand = model.getPlayerHand(player);
    Grid grid = model.getGrid();

    Move bestMove = null;
    int maxFlips = -1;

    // Iterate over all cards in hand and all positions on grid
    for (ICard card : hand) {
      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getColumns(); col++) {
          if (grid.getCell(row, col).isEmpty()) {
            // Simulate placing this card at this position
            int flips = model.getNumCardsAbleToFlip(player, card, row, col);
            Move currentMove = new Move(card, row, col);

            // If this move flips more cards than previous best, choose it
            if (flips > maxFlips) {
              bestMove = new Move(card, row, col);
              maxFlips = flips;
            } else if (flips == maxFlips) {
              bestMove = findUpperLeft(row, col,
                      Objects.requireNonNull(bestMove), Objects.requireNonNull(currentMove));
            }
          }
        }
      }
    }

    if (bestMove != null) {
      return bestMove;
    } else {
      return Move.findFallbackMove(hand, grid, model, player);
    }
  }

  private Move findUpperLeft(int row, int col, Move currentBest, Move currentMove) {
    // Break ties by choosing upper-leftmost coordinate
    // Compare the two moves and return the one that is at the uppermost-leftmost position
    if (currentMove.getRow() < currentBest.getRow()
            || (currentMove.getRow() == currentBest.getRow()
            && currentMove.getCol() < currentBest.getCol())) {
      return currentMove;
    }
    return currentBest;
  }
}
