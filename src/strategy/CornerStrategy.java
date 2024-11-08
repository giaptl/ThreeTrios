package strategy;

import java.util.List;

import model.Card;
import model.Grid;
import model.Player;
import model.ReadOnlyThreeTriosModel;

public class CornerStrategy implements Strategy {

  @Override
  public Move selectMove(Player player, ReadOnlyThreeTriosModel model) {
    List<Card> hand = model.getPlayerHand(player);
    Grid grid = model.getGrid();

    // Define corner positions: top-left, top-right, bottom-left, bottom-right
    int[][] corners = {{0, 0}, {0, grid.getColumns() - 1}, {grid.getRows() - 1, 0}, {grid.getRows() - 1, grid.getColumns() - 1}};

    for (int[] corner : corners) {
      int row = corner[0];
      int col = corner[1];

      if (grid.getCell(row, col).isEmpty()) {
        // Return first available corner with index-0 card from hand
        return new Move(hand.get(0), row, col);
      }
    }

    // If no corners are available or valid moves found:
    return Move.findFallbackMove(hand, grid);
  }
}
