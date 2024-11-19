package model;

/**
 * The Cell interface represents a cell in the game grid.
 * It provides methods to check the state of the cell and retrieve information about the card
 * and player associated with the cell.
 */
public interface Cell {

  /**
   * Checks if the cell is a hole.
   *
   * @return true if the cell is a hole, false otherwise.
   */
  boolean isHole();

  /**
   * Retrieves the card associated with the cell.
   *
   * @return the card in the cell, or null if the cell is empty.
   */
  Card getCard();

  /**
   * Retrieves the owner of the cell.
   *
   * @return the player who owns the cell, or null if the cell is not owned by any player.
   */
  IPlayer getOwner();

  /**
   * Checks if the cell is empty.
   *
   * @return true if the cell is empty, false otherwise.
   */
  boolean isEmpty();
}
