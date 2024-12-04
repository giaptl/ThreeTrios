package model;

import java.util.List;

import model.cards.Card;
import model.grid.Cell;
import players.Player;

/**
 * Interface representing the core methods for the Three Trios game model.
 */
public interface ThreeTriosModel extends ReadOnlyThreeTriosModel {


  /**
   * Initializes the game with a deck, a board, and determines if the deck should be shuffled.
   *
   * @param deck the list of cells to load into the game.
   * @param board the board on which the game will be played.
   * @param shuffle a flag indicating whether the deck should be shuffled.
   */
  void startGame(List<Cell> deck, Cell[][] board, boolean shuffle);

  /**
   * Places a card on the grid for a specific player at the given position.
   *
   * @param player the player placing the card.
   * @param card the card to be placed on the grid.
   * @param row the row index where the card will be placed.
   * @param col the column index where the card will be placed.
   * @return true if the card was placed successfully, false otherwise.
   */
  boolean placeCard(Player player, Card card, int row, int col);
  /**
   * Checks if placing a card is a legal move for the player.
   *
   * @param player the player attempting the move
   * @param card the card to be placed
   * @param row the row index of the grid
   * @param col the column index of the grid
   * @return true if the move is legal, false otherwise
   */
  boolean isLegalMove(Player player, Card card, int row, int col);

  /**
   * Gets the next card for the current player.
   *
   * @param currentPlayer the player whose next card is requested
   * @return the next card, or null if no cards are available
   */
  Card getNextCardForPlayer(Player currentPlayer);
}
