package view;

import java.util.List;

import model.Card;
import model.Cell;
import model.Direction;
import model.Grid;
import model.Player;

public class TextView implements IGameView {

  public TextView() {

  }

  public void renderGrid(Grid grid) {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        Cell cell = grid.getCell(row, col);
        if (cell.isHole()) {
          System.out.print(" ");
        } else if (cell.isEmpty()) {
          System.out.print("_");
        } else {
          Player owner = cell.getOwner();
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

  public void renderPlayerHand(Player player, List<Card> hand) {
    System.out.println("Player: " + player.getName().toUpperCase());
    System.out.println("Hand:");
    for (Card card : hand) {
      System.out.println(card.getName() +
              " " + card.getAttackValue(Direction.NORTH) +
              " " + card.getAttackValue(Direction.SOUTH) +
              " " + card.getAttackValue(Direction.EAST) +
              " " + card.getAttackValue(Direction.WEST));
    }
    System.out.print("\n");
  }
}
