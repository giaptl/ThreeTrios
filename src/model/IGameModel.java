package model;

import java.util.List;

/**
 * Interface representing the model for the Three Trios game.
 * This interface defines the core functionalities required to manage the game state,
 * enforce game rules, and facilitate player actions.
 */
public interface IGameModel {

  /**
   * Starts the game using the given grid and list of cards. (Second way to start the game in the
   * case where configuration files are given). Cards are handed out to player red first, then to
   * player blue. The number of cards in the deck must be at least the number of CardCells in the
   * grid. The number of card cells also must be odd.
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

  /**
   * Allows a player to play a card at the specified position on the grid. That position on the
   * grid must be empty and a valid position for the card to be placed (CardCell). Card can NOT be
   * placed on a Hole. The card must be in the player's hand and the player must be the current
   * player. After playing the card, the current player is switched to the other player,
   * regardless of whether a battle phase occurs or not.
   *
   * @param player the player who is playing the card
   * @param card   the card to be played
   * @param row    the row position on the grid
   * @param col    the column position on the grid
   * @throws IllegalArgumentException if the player is not the current player
   * @throws IllegalArgumentException if the card is not in the player's hand
   * @throws IllegalArgumentException if the cell at the specified position is not empty
   * @throws IllegalArgumentException if the card is not placed in a valid position
   * @throws IllegalArgumentException if the game is over
   */
  void playCard(Player player, Card card, int row, int col);

  /**
   * Starts the battle phase at the specified position on the grid. The battle phase occurs between
   * 2 cards that are adjacent to each other. The card at the specified position must be the card
   * of the current player. A player's card cannot battle with another card of the same player,
   * only its opponent's card.
   *
   * @param row the row position on the grid
   * @param col the column position on the grid
   * @throws IllegalArgumentException if the cell at the specified position is empty.
   */
  void startBattlePhase(int row, int col);
}