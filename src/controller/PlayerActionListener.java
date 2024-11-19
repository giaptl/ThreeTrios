package controller;

import model.Player;

public interface PlayerActionListener {

  /**
   * Called when a player selects a card from their hand.
   *
   * @param player the player who selected the card
   * @param cardIndex the index of the selected card in the player's hand
   */
  void onCardSelected(Player player, int cardIndex);

  /**
   * Called when a player selects a grid cell to place a card.
   *
   * @param row the row index of the selected grid cell
   * @param col the column index of the selected grid cell
   */
  void onGridCellSelected(int row, int col);
}
