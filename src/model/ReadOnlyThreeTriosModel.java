package model;

import java.util.List;

public interface ReadOnlyThreeTriosModel {

  /**
   * Gets the current player whose turn it is to play.
   *
   * @return the current player
   */
  Player getCurrentPlayer();

  /**
   * Gets the grid on which the game is being played.
   *
   * @return the current game grid
   */
  Grid getGrid();

  /**
   * Gets the hand of cards (list of Cards) for the specified player.
   *
   * @param player the player whose hand is to be retrieved
   * @return the list of cards in the player's hand
   */
  List<Card> getPlayerHand(Player player);

  /**
   * Checks if the game is over. Game is over when all empty card cells are filled.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();


  /**
   * Gets the winner of the game. The winner of the game is determined by the player who has the
   * most cards on the grid and in their hand combined. The game CAN end in a tie if both players
   * have the same number of cards.
   *
   * @return the player who won the game, or null if the game is a tie or not yet finished
   */
  Player getWinner();

}