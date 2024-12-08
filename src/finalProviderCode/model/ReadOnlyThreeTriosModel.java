package finalProviderCode.model;

import java.util.List;

/**
 * Represents a read-only view of the ThreeTriosModel.
 * This interface provides access to information about the current game state
 * without allowing modifications to the game model.
 */
public interface ReadOnlyThreeTriosModel {


  /**
   * Checks if the grid is completely full.
   *
   * @return true if the grid is full, false otherwise
   */
  boolean isFull();


  /**
   * Counts the number of cards owned by the specified player on the grid.
   *
   * @param player the player whose cards are being counted
   * @return the number of cards owned by the player
   */
  int countOwnedCards(Player player);

  /**
   * Renders the game state.
   */
  void render();

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Gets the winner of the game.
   *
   * @return the winning player, or null if there is no winner
   */
  Player getWinner();

  /**
   * Gets a string representation of the grid.
   *
   * @return a string representation of the grid
   */
  String getGridView();

  /**
   * Gets the owner of a specific cell in the grid.
   *
   * @param r the row index of the cell
   * @param c the column index of the cell
   * @return the owner of the cell, or null if the cell is empty
   */
  Object getCellOwner(int r, int c);

  /**
   * Gets the value of the card at a specific cell in the grid.
   *
   * @param r the row index of the cell
   * @param c the column index of the cell
   * @return the value of the card, or an empty string if the cell is empty
   */
  String getCardValueAt(int r, int c);

  /**
   * Determines if a move is legal based on the game rules.
   *
   * @param player the player making the move
   * @param card the card being placed
   * @param row the row index of the grid
   * @param col the column index of the grid
   * @return true if the move is legal, false otherwise
   */
  boolean isLegalMove(Player player, Card card, int row, int col);

  /**
   * Checks if a specific cell in the grid is occupied.
   *
   * @param r the row index of the cell
   * @param c the column index of the cell
   * @return true if the cell is occupied, false otherwise
   */
  boolean isCellOccupied(int r, int c);

  /**
   * Gets the number of columns in the grid.
   *
   * @return the number of columns in the grid
   */
  int getGridCols();

  /**
   * Gets the number of rows in the grid.
   *
   * @return the number of rows in the grid
   */
  int getGridRows();

  /**
   * Gets the hand of cards for a specific player.
   *
   * @param player the player whose hand is requested
   * @return the list of cards in the player's hand
   */
  List<Card> getPlayerHand(Player player);

  /**
   * Gets the red player.
   *
   * @return the red player
   */
  Player getRedPlayer();

  /**
   * Gets the blue player.
   *
   * @return the blue player
   */
  Player getBluePlayer();

  /**
   * Gets the number of potential card flips for a player at a specific position.
   *
   * @param player the player making the move
   * @param card the card being placed
   * @param row the row index of the grid
   * @param col the column index of the grid
   * @return the number of potential flips for the move
   */
  int getPotentialFlips(Player player, Card card, int row, int col);

  /**
   * Gets the number of potential opponent card flips for a player at a specific position.
   *
   * @param player the player making the move
   * @param card the card being placed
   * @param row the row index of the grid
   * @param col the column index of the grid
   * @return the number of potential opponent flips for the move
   */
  int getPotentialOpponentFlips(Player player, Card card, int row, int col);

  /**
   * Gets the grid object.
   *
   * @return the grid object
   */
  // Grid getGrid();

  /**
   * Gets the current player in the game.
   *
   * @return the current player
   */
  Player getCurrentPlayer();


}
