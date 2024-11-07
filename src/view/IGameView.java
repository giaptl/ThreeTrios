package view;

import java.util.List;

import model.Card;
import model.Grid;
import model.Player;

/**
 * Interface for the GameView class. Represents a text view for the class and can be changed later.
 */
public interface IGameView {

  /**
   * Renders the game grid.
   *
   * @param grid the grid to be rendered
   */
  void renderGrid(Grid grid);

  /**
   * Renders the hand of the specified player.
   *
   * @param player the player whose hand is to be rendered
   * @param hand the list of cards in the player's hand
   */
  void renderPlayerHand(Player player, List<Card> hand);


}
