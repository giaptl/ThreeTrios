package model;

import java.util.List;

/**
 * Interface representing the model for the Three Trios game.
 * This interface defines the core functionalities required to manage the game state,
 * enforce game rules, and facilitate player actions.
 * Game Objective: Players take turns placing cards on the grid and battling adjacent cards.
 * The goal is to have the most cards on the grid and in hand at the end of the game.
 */
public interface ThreeTriosModel extends ReadOnlyThreeTriosModel {

  /**
   * Starts the game using the given grid and list of cards. (We are assuming that we will ALWAYS
   * be given config files for card and grid data). Cards are handed out to player red first, then
   * to player blue. The number of cards in the deck must be at least the number of CardCells in the
   * grid. The number of card cells also must be odd.  Each player receives (N+1)/2 cards, where N
   * is the number of card cells on the grid. Player red goes first
   *
   * @param grid the grid on which the game will be played
   * @param cards the list of cards to be used in the game
   * @param shuffle true if the cards should be shuffled, false otherwise
   * @throws IllegalArgumentException if the number of card cells is not odd
   * @throws IllegalArgumentException if the number of cards in the deck are less than the number
   *                                  of CardCells in the grid + 1
   */
  void startGameWithConfig(Grid grid, List<Card> cards, boolean shuffle, IPlayer player1, IPlayer player2);


  /**
   * Allows a player to play a card at the specified position on the grid. That position on the
   * grid must be empty and a valid position for the card to be placed (CardCell). Card can NOT be
   * placed on a Hole. The card must be in the player's hand and the player must be the current
   * player. After playing the card, the current player is switched to the other player,
   * regardless of whether a battle phase occurs or not. After playing a card, a battle phase
   * occurs automatically for the newly placed card if there are adjacent cards that are owned by
   * the opponent; otherwise, the turn is over. The card is removed from the player's hand after it
   * is played.
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
  void playCard(IPlayer player, Card card, int row, int col);

  /**
   * Starts the battle phase at the specified position on the grid. The battle phase occurs between
   * 2 cards that are adjacent to each other. The card at the specified position must be the card
   * of the current player. A player's card cannot battle with another card of the same player,
   * only its opponent's card. Cannot be called if a playCard() has not already happened. If the
   * battle phase results in a tie, there is no change in the owner of cards. The player that wins
   * the battle phase is the new owner of the card they won against.
   *
   * @param row the row position on the grid
   * @param col the column position on the grid
   * @throws IllegalArgumentException if the cell at the specified position is empty.
   */
  void startBattlePhase(int row, int col);


  /**
   * Adds a model status listener to the model.
   *
   * @param listener the listener to be added
   */
  void addModelStatusListener(ModelStatusListener listener);

  void removeModelStatusListener(ModelStatusListener listener);
}