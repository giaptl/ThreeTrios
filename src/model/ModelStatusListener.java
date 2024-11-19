package model;

public interface ModelStatusListener {

  /**
   * Called when it is a player's turn to make a move.
   *
   * @param currentPlayer the player whose turn it is
   */
  void onPlayerTurn(Player currentPlayer);
  void gameOver(Player winner);
}
