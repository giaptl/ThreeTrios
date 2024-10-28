package model;

import java.util.List;

/**
 * Interface representing the model for the Three Trios game.
 * This interface defines the core functionalities required to manage the game state,
 * enforce game rules, and facilitate player actions.
 */
public interface IGameModel {

  /**
   * Starts the game with a grid created from the specified number of rows and columns. (Default
   * way to start the game if no configuration files are given)
   *
   * @param shuffle true if the cards should be shuffled, false otherwise
   * @param row the number of rows in the grid
   * @param col the number of column in the grid
   */
  void startGameDefault(boolean shuffle, int row, int col);

  /**
   * Starts the game using the given grid and list of cards (Second way to start the game in the
   * case where configuration files are given)
   *
   * @param grid the grid on which the game will be played
   * @param cards the list of cards to be used in the game
   * @param shuffle true if the cards should be shuffled, false otherwise
   * @throws IllegalArgumentException if the number of card cells is not odd
   * @throws IllegalArgumentException if the number of cards in the deck are less than the number
   *                                  of CardCells in the grid + 1
   */
  void startGameWithConfig(Grid grid, List<Card> cards, boolean shuffle);

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
   * Gets the hand of cards for the specified player.
   *
   * @param player the player whose hand is to be retrieved
   * @return the list of cards in the player's hand
   */
  List<Card> getPlayerHand(Player player);

  /**
   * Checks if the game is over. Game is over when all empty card cells are filled
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Gets the winner of the game.
   *
   * @return the player who won the game, or null if the game is a tie or not yet finished
   */
  Player getWinner();

  /**
   * Allows a player to play a card at the specified position on the grid.
   *
   * @param player the player who is playing the card
   * @param card   the card to be played
   * @param row    the row position on the grid
   * @param col    the column position on the grid
   */
  void playCard(Player player, Card card, int row, int col);

  /**
   * Starts the battle phase at the specified position on the grid.
   *
   * @param row the row position on the grid
   * @param col the column position on the grid
   */
  void startBattlePhase(int row, int col);
}