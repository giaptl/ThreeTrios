package strategy;

import java.util.List;

import model.Card;
import model.Grid;

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

  public static Move findFallbackMove(List<Card> hand, Grid grid) {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        if (grid.getCell(row, col).isEmpty()) {
          return new Move(hand.get(0), row, col);
        }
      }
    }
    return null; // No valid moves found
  }
}

