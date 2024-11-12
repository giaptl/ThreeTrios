package strategy;

import java.util.List;

import model.Card;
import model.Grid;
import model.Player;
import model.ReadOnlyThreeTriosModel;

public class LeastLikelyFlippedStrategy implements Strategy {

  @Override
  public Move selectMove(Player player, ReadOnlyThreeTriosModel model) {
    List<Card> hand = model.getPlayerHand(player);
    Grid grid = model.getGrid();

    Move bestMove = null;
    int minFlipRisk = Integer.MAX_VALUE;

    for (Card card : hand) {
      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getColumns(); col++) {
          if (grid.getCell(row, col).isEmpty()) {
            // Calculate the flip risk for this card at this position
            int flipRisk = calculateFlipRisk(card, row, col, player, model);

            if (flipRisk < minFlipRisk || (flipRisk == minFlipRisk && isUpperLeft(row, col, bestMove))) {
              bestMove = new Move(card, row, col);
              minFlipRisk = flipRisk;
            }
          }
        }
      }
    }

    return bestMove != null ? bestMove : Move.findFallbackMove(hand, grid, model, player);
  }

  private int calculateFlipRisk(Card card, int row, int col, Player player, ReadOnlyThreeTriosModel model) {
    int flipRisk = 0;
    Player opponent = model.getOpponent(player);
    List<Card> opponentHand = model.getPlayerHand(opponent);

    for (Card opponentCard : opponentHand) {
      // Simulate placing the opponent's card at each position and calculate flips
      for (int oppRow = 0; oppRow < model.getGrid().getRows(); oppRow++) {
        for (int oppCol = 0; oppCol < model.getGrid().getColumns(); oppCol++) {
          if (model.getGrid().getCell(oppRow, oppCol).isEmpty()) {
            int flips = model.getNumCardsAbleToFlip(opponent, opponentCard, oppRow, oppCol);
            if (flips > 0) {
              flipRisk++;
            }
          }
        }
      }
    }

    return flipRisk;
  }

  private boolean isUpperLeft(int row, int col, Move currentBest) {
    if (currentBest == null) return true;
    return row < currentBest.getRow() || (row == currentBest.getRow() && col < currentBest.getCol());
  }

}
