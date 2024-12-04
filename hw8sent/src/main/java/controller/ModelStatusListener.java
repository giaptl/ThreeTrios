package controller;

/**
 * Interface for receiving notifications from the game model about its status.
 * Controllers implementing this interface can respond to these updates accordingly.
 */
public interface ModelStatusListener {

  /**
   * Notifies the controller that it is a player's turn.
   *
   * @param playerName The name of the player whose turn it is.
   */
  void onPlayerTurn(String playerName);

  /**
   * Notifies the controller that the game is over.
   *
   * @param winnerName The name of the winning player.
   * @param winningScore The score of the winning player.
   */
  void onGameOver(String winnerName, int winningScore);

  /**
   * Notifies the controller of an invalid move attempt.
   *
   * @param errorMessage The error message describing why the move was invalid.
   */
  void onInvalidMove(String errorMessage);

  /**
   * Notifies the controller that the model state has changed.
   * This is typically used for updates like grid changes or score updates.
   */
  void onModelUpdated();
}
