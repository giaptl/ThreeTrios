package strategy;

import java.util.List;
import java.util.Objects;

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
            Move currentMove = new Move(card, row, col);

            // If this move flips more cards than previous best, choose it
            if (flips > maxFlips) {
              bestMove = new Move(card, row, col);
              maxFlips = flips;
            } else if (flips == maxFlips) {
              bestMove = findUpperLeft(row, col, Objects.requireNonNull(bestMove), Objects.requireNonNull(currentMove));
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
//    System.out.println("CurrentBest: " + currentBest.getRow() + " " + currentBest.getCol());
//    System.out.println("NewMove: " + currentMove.getRow() + " " + currentMove.getCol());
    if (currentMove.getRow() < currentBest.getRow() ||
            (currentMove.getRow() == currentBest.getRow() && currentMove.getCol() < currentBest.getCol())) {
      return currentMove;
    }
    return currentBest;
  }
}
