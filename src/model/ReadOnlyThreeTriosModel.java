package model;

import java.util.List;

/**
 * Represents a read-only view of the ThreeTrios game model. All these methods are immutable and
 * cannot change the state of the game. Only observe the game state. Needed for view since we do not
 * want the view to be able to change the game state.
 */
public interface ReadOnlyThreeTriosModel {

  /**
   * Gets the current player whose turn it is to play.
   *
   * @return the current player
   */
  IPlayer getCurrentPlayer();

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
  List<Card> getPlayerHand(IPlayer player);

  /**
   * Gets the red player.
   *
   * @return the red player
   */
  IPlayer getRedPlayer();

  /**
   * Gets the blue player.
   *
   * @return the blue player
   */
  IPlayer getBluePlayer();

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
  IPlayer getWinner();

  /**
   * Gets the score of the specified player. The score of a player is the number of cards they have
   * on the grid plus the number of cards they have left in their hand at any given time.
   *
   * @param player the player whose score is to be retrieved (red or blue)
   * @return the score of the player
   */
  int getPlayerScore(IPlayer player);

  /**
   * Returns the number of cards that able to be flipped by placing a certain card from a player's
   * hand to a spot on the board. This method is especially helpful for determining the best move
   * that can be played for a player.
   *
   * @param player represents the player whose card will be played onto the board.
   * @param card represents the card that will be played to the board.
   * @param row represents the row of the board that the card will be played to.
   * @param col represents the column of the board that the card will be played to.
   */
  int getNumCardsAbleToFlip(IPlayer player, Card card, int row, int col);

  /**
   * Returns the opponent of the player who is currently playing.
   * Used primarily in the AI strategies that were implemented.
   *
   * @param player represents which player's turn it is right now and who the opponent is.
   */
  IPlayer getOpponent(IPlayer player);
}
