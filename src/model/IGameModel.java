package model;

import java.util.List;

/**
 * Interface representing the model for the Three Trios game.
 * This interface defines the core functionalities required to manage the game state,
 * enforce game rules, and facilitate player actions.
 */
public interface IGameModel {

  /**
   * Starts the game with the given grid and list of cards.
   *
   * @param grid  the grid on which the game will be played
   * @param cards the list of cards to be used in the game
   */
  void startGame(Grid grid, List<Card> cards, boolean shuffle, int row, int col, boolean configFile);

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