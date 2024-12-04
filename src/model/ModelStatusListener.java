package model;

import player.IPlayer;

/**
 * The PlayerActionListener interface defines methods to handle player actions in the game.
 * Implementations of this interface will respond to various player actions such as selecting a
 * card, selecting a grid cell, selecting a move, and when it becomes a player's turn.
 */
public interface ModelStatusListener {

  /**
   * Called when it is a player's turn to make a move.
   *
   * @param currentPlayer the player whose turn it is
   */
  void onPlayerTurn(IPlayer currentPlayer);

  /**
   * Called when the game is over.
   *
   *
   * @param winner the player who won the game, or null if the game ended in a tie
   */
  void gameOver(IPlayer winner);

  /**
   * Called when a card is played on the grid.
   * @param player the player who played the card
   * @param card the card that was played
   * @param row the row where the card was played
   * @param col the column where the card was played
   */
  void onCardPlayed(IPlayer player, ICard card, int row, int col);
}
