package strategy;

import java.util.List;

import model.Card;
import model.Grid;
import model.IPlayer;
import model.ReadOnlyThreeTriosModel;

/**
 * Represents a move in the ThreeTrios game, consisting of a card and its position on the grid.
 */
public class Move {
  private final Card card;
  private final int row;
  private final int col;

  /**
   * Constructs a Move with the specified card and position.
   *
   * @param card the card to be placed
   * @param row the row position on the grid
   * @param col the column position on the grid
   */
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

  /**
   * Finds a fallback move when no optimal move is found. This method selects the empty cell
   * closest to the top-left corner of the grid and chooses the best card from the player's hand
   * for that position.
   *
   * @param hand the list of cards in the player's hand
   * @param grid the current game grid
   * @param model the read-only model of the game
   * @param player the player making the move
   * @return a Move representing the fallback move, or null if no valid move is found
   */
  public static Move findFallbackMove(List<Card> hand, Grid grid,
                                      ReadOnlyThreeTriosModel model, IPlayer player) {
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
