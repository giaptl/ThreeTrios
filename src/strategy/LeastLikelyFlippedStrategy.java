package strategy;

import java.util.List;

import model.Card;
import model.CardCell;
import model.Grid;
import model.IPlayer;
import model.ReadOnlyThreeTriosModel;

/**
 * Strategy that selects the move that is least likely to be flipped by the opponent.
 * This is done by calculating the flip risk for each possible move and selecting the one with the
 * least flip risk. We consider "hardest to flip" to be the move in which the opponent can flip
 * the least number of the current player's cards in the following move.
 */
public class LeastLikelyFlippedStrategy implements Strategy {

  @Override
  public Move selectMove(IPlayer player, ReadOnlyThreeTriosModel model) {
    List<Card> hand = model.getPlayerHand(player);
    Grid grid = model.getGrid();

    Move bestMove = null;
    int minFlipRisk = Integer.MAX_VALUE;

    for (Card card : hand) {
      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getColumns(); col++) {
          if (grid.getCell(row, col).isEmpty()) {
            int flipRisk = calculateFlipRisk(card, row, col, player, model);
            if (flipRisk < minFlipRisk
                    || (flipRisk == minFlipRisk && isUpperLeft(row, col, bestMove))) {
              bestMove = new Move(card, row, col);
              minFlipRisk = flipRisk;
            }
          }
        }
      }
    }

    return bestMove != null ? bestMove : Move.findFallbackMove(hand, grid, model, player);
  }

  private int calculateFlipRisk(Card card, int row, int col,
                                IPlayer player, ReadOnlyThreeTriosModel model) {
    int flipRisk = 0;
    IPlayer opponent = model.getOpponent(player);
    List<Card> opponentHand = model.getPlayerHand(opponent);

    // Simulate placing the card
    Grid simulatedGrid = model.getGrid().copyOfGrid();
    simulatedGrid.setCell(row, col, new CardCell(card, player));

    for (Card opponentCard : opponentHand) {
      for (int oppRow = 0; oppRow < simulatedGrid.getRows(); oppRow++) {
        for (int oppCol = 0; oppCol < simulatedGrid.getColumns(); oppCol++) {
          if (simulatedGrid.getCell(oppRow, oppCol).isEmpty()) {
            int flips = model.getNumCardsAbleToFlip(opponent, opponentCard, oppRow, oppCol);
            flipRisk += flips;
          }
        }
      }
    }

    return flipRisk;
  }

  private boolean isUpperLeft(int row, int col, Move currentBest) {
    if (currentBest == null) {
      return true;
    }
    return row < currentBest.getRow()
            || (row == currentBest.getRow() && col < currentBest.getCol());
  }

}
