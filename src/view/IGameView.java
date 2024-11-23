package view;

import controller.PlayerActionListener;
import model.Card;
import player.IPlayer;
/**
 * Interface representing the view component of the ThreeTrios game.
 * The IGameView interface defines methods for updating and managing the visual
 * representation of the game, including displaying the player's hands, grid cells,
 * and handling user interactions.
 */
public interface IGameView {

  /**
   * Updates the display of a specific cell in the grid with the provided card.
   * This is called when a card is placed on the grid,
   * updating the cell to display the card's values.
   *
   * @param row the row index of the grid cell to update
   * @param col the column index of the grid cell to update
   * @param card the card to display in the specified grid cell
   */
  void updateGridCell(int row, int col, Card card);

  /**
   * Removes a specific card from the player's hand panel.
   * This is typically called after the player places the card on the grid.
   *
   * @param player the player whose hand panel will be updated (either red or blue player)
   * @param card the card to remove from the player's hand panel
   */
  void removeCardFromHandPanel(IPlayer player, Card card);

  /**
   * Refreshes the view to reflect the latest state of the game model.
   * This includes updating the grid and hand panels to display the current cards and layout.
   */
  void refreshView();

  /**
   * Displays an error message in a dialog box, typically when an invalid move is attempted or
   * when the game encounters an error.
   *
   * @param message the error message to display
   */
  void showError(String message);

  /**
   * Updates the visual indication of a selected card in the player's hand.
   * This highlights the selected card and removes any previous highlights.
   *
   * @param player the player who owns the selected card (either red or blue player)
   * @param card the card that is selected
   */
  void updateCardSelection(IPlayer player, Card card);



  /**
   * Adds a player action listener to the view.
   *
   * @param listener the listener to be added
   */
  void addPlayerActionListener(PlayerActionListener listener);

  /**
   * Removes a PlayerActionListener from view.
   *
   * @param listener the listener to remove
   */
  void removePlayerActionListener(PlayerActionListener listener);

  /**
   * Displays the game over message with the winner and the winning score.
   *
   * @param winner the player who won the game
   * @param score the winning score
   */
  void showGameOver(IPlayer winner, int score);
}
