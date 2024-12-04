package view;

import java.util.List;

import model.Card;
import model.Cell;
import model.Direction;
import model.Grid;
import model.ICard;
import player.IPlayer;

/**
 * A text based view for the game. Default view but will be changed to a GUI in the future.
 */
public class TextView {


  /**
   * Renders the game grid in a text format. Holes are represented by spaces, empty cells by
   * underscores, and cells occupied by players' cards are represented by 'R' for Red player
   * and 'B' for Blue player.
   *
   * @param grid the game grid to be rendered
   */
  protected void renderGrid(Grid grid) {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        Cell cell = grid.getCell(row, col);
        if (cell.isHole()) {
          System.out.print(" ");
        } else if (cell.isEmpty()) {
          System.out.print("_");
        } else {
          IPlayer owner = cell.getOwner();
          if (owner.getName().equals("Red")) {
            System.out.print("R");
          } else if (owner.getName().equals("Blue")) {
            System.out.print("B");
          }
        }
      }
      System.out.println();
    }
  }

  /**
   * Renders the player's hand in a text format. Each card is displayed with its name and
   * attack values in the four directions (north, south, east, and west).
   *
   * @param player the player whose hand is to be rendered
   * @param hand the list of cards in the player's hand
   */
  protected void renderPlayerHand(IPlayer player, List<ICard> hand) {
    System.out.println("Player: " + player.getName().toUpperCase());
    System.out.println("Hand:");
    for (ICard card : hand) {
      System.out.println(card.getName()
              + " " + card.getAttackValue(Direction.NORTH)
              + " " + card.getAttackValue(Direction.SOUTH)
              + " " + card.getAttackValue(Direction.EAST)
              + " " + card.getAttackValue(Direction.WEST));
    }
    System.out.print("\n");
  }
}
