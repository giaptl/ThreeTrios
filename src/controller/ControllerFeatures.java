package controller;

import model.Player;

public interface ControllerFeatures {

  /**
   * Handles the event when a card in a player's hand is clicked.
   * If the clicked card is already selected, it will be deselected.
   * Otherwise, the clicked card will be selected.
   *
   * @param player the player who clicked the card
   * @param cardIndex the index of the clicked card in the player's hand
   */
  void handleCardClick(Player player, int cardIndex);


  /**
   * Handles the event when a grid cell is clicked.
   * If a card is selected, it attempts to play the card at the specified grid cell.
   * If the move is valid, the grid cell is updated, the card is removed from the player's hand,
   * and the view refreshes. If the move is invalid, an error message displays.
   *
   * @param row the row index of the clicked grid cell
   * @param col the column index of the clicked grid cell
   */
  void handleGridClick(int row, int col);
}
