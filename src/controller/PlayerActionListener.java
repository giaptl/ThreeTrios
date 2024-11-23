package controller;


import player.IPlayer;
import strategy.Move;

/**
 * The PlayerActionListener interface defines methods to handle player actions in the game.
 * Implementations of this interface will respond to various player actions such as selecting a
 * card, selecting a grid cell, selecting a move, and when it becomes a player's turn.
 */
public interface PlayerActionListener {

  /**
   * Called when a player selects a card from their hand.
   *
   * @param player the player who selected the card
   * @param cardIndex the index of the selected card in the player's hand
   */
  void onCardSelected(IPlayer player, int cardIndex);

  /**
   * Called when a player selects a grid cell to place a card.
   *
   * @param row the row index of the selected grid cell
   * @param col the column index of the selected grid cell
   */
  void onGridCellSelected(int row, int col);

  /**
   * Invoked when a move is selected by the player.
   *
   * @param move the selected move
   */
  void onMoveSelected(Move move);

  /**
   * Called when it becomes a player's turn.
   *
   * @param player the player whose turn it is
   */
  void onPlayerTurn(IPlayer player);
}
