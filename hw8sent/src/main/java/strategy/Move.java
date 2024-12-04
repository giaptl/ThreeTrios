package strategy;

import model.cards.Card;

/**
 * Represents a move in the Three Trios game.
 * Encapsulates the row, column, and card associated with a move.
 */
public class Move {
  private final int row;
  private final int col;
  private final Card card;

  /**
   * Constructs a {@code Move} with the specified row, column, and card.
   *
   * @param row the row index of the move
   * @param col the column index of the move
   * @param card the card to be placed at the specified row and column
   */
  public Move(int row, int col, Card card) {
    this.row = row;
    this.col = col;
    this.card = card;
    // The constructor is intentionally simple and only initializes the fields.
  }

  // Getters for row, col, and card (optional, if needed in your code)
  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Card getCard() {
    return card;
  }
}
