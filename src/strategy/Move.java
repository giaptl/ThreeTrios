package strategy;

import java.util.List;

import model.Card;
import model.Grid;
import model.Player;
import model.ReadOnlyThreeTriosModel;

public class Move {
  private final Card card;
  private final int row;
  private final int col;

  public Move(Card card, int row, int col) {
    this.card = card;
    this.row = row;
    this.col = col;
  }

  public Card getCard() {
    return card;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public static Move findFallbackMove(List<Card> hand, Grid grid,
                                      ReadOnlyThreeTriosModel model, Player player) {
    int minDistance = Integer.MAX_VALUE;
    int bestRow = -1;
    int bestCol = -1;

    // Find the empty cell closest to top-left corner
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        if (grid.getCell(row, col).isEmpty()) {
          // Calculate Manhattan distance from (0,0)
          int distance = row + col;
          if (distance < minDistance) {
            minDistance = distance;
            bestRow = row;
            bestCol = col;
          }
        }
      }
    }

    if (bestRow != -1) {
      // Find best card for this position (closest to index 0)
      Card bestCard = hand.get(0);
      int bestScore = -999;

      for (Card card : hand) {
        int score = model.getNumCardsAbleToFlip(player, card, bestRow, bestCol);
        if (score > bestScore) {
          bestScore = score;
          bestCard = card;
        }
      }
      return new Move(bestCard, bestRow, bestCol);
    }
    System.out.println("Should never be reached. Fallback move should always be found");
    return null;
  }
}
