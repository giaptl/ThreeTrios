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
  void startGameWithConfig(Grid grid, List<Card> cards, boolean shuffle);





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

}