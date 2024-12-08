package strategy;

import java.util.List;

import model.Grid;
import model.ICard;
import model.ReadOnlyThreeTriosModel;
import player.IPlayer;

/**
 * Represents a move in the ThreeTrios game, consisting of a card and its position on the grid.
 */
public class Move {
  private final ICard card;
  private final int row;
  private final int col;

  /**
   * Constructs a Move with the specified card and position.
   *
   * @param card the card to be placed
   * @param row  the row position on the grid
   * @param col  the column position on the grid
   */
  public Move(ICard card, int row, int col) {
    this.card = card;
    this.row = row;
    this.col = col;
  }

  public ICard getCard() {
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
   * @param hand   the list of cards in the player's hand
   * @param grid   the current game grid
   * @param model  the read-only model of the game
   * @param player the player making the move
   * @return a Move representing the fallback move, or null if no valid move is found
   */
  public static Move findFallbackMove(List<ICard> hand, Grid grid,
                                      ReadOnlyThreeTriosModel model, IPlayer player) {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        if (grid.getCell(row, col).isEmpty()) {
          // Select the first card in the hand for the fallback move
          ICard fallbackCard = hand.get(0);
          return new Move(fallbackCard, row, col);
        }
      }
    }
    System.out.println("Should never be reached. Fallback move should always be found");
    return null;
  }
}
