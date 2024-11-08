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
            // Simulate placing this card at this position
            int flipRisk = model.getNumCardsAbleToFlip(player, card, row, col);

            if (flipRisk < minFlipRisk || (flipRisk == minFlipRisk && isUpperLeft(row, col))) {
              bestMove = new Move(card, row, col);
              minFlipRisk = flipRisk;
            }
          }
        }
      }
    }

    return bestMove != null ? bestMove : Move.findFallbackMove(hand, grid);
  }

  private boolean isUpperLeft(int row, int col) {
    return row == 0 && col == 0;
  }

}
