package model;

import player.IPlayer;

public interface ModelStatusListener {

  /**
   * Called when it is a player's turn to make a move.
   *
   * @param currentPlayer the player whose turn it is
   */
  void onPlayerTurn(IPlayer currentPlayer);

  void gameOver(IPlayer winner);

  void onCardPlayed(IPlayer player, Card card, int row, int col);
}
