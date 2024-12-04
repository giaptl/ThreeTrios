package model.managers;

import model.grid.Grid;
import players.Player;
import model.cards.Card;

/**
 * Manages the overall flow of the game, including handling player turns,
 * placing cards on the grid, and determining when the game ends.
 */
public class GameManager {

  private final Grid grid;

  /**
   * Constructs a {@code GameManager} with the specified grid and players.
   *
   * @param grid the game grid
   * @param redPlayer the red player
   * @param bluePlayer the blue player
   */
  public GameManager(Grid grid, Player redPlayer, Player bluePlayer) {
    this.grid = grid;
    TurnManager turnManager = new TurnManager(redPlayer, bluePlayer);
  }

  /**
   * Starts the game by displaying an initial message and rendering the grid.
   */
  public void startGame() {
    System.out.println("Game started!");
    displayGrid();
  }

  /**
   * Handles a player's turn by placing a card on the grid at the specified location.
   * If the card is successfully placed, the grid is displayed, and the turn switches
   * to the next player.
   *
   * @param card the card to be placed
   * @param row the row index where the card will be placed
   * @param col the column index where the card will be placed
   * @return {@code true} if the move is successful, {@code false} otherwise
   */
  public boolean playTurn(Card card, int row, int col) {
    return row >= 0 && row < grid.getRows() && col >= 0 && col <
      grid.getCols() && !grid.isOccupied(row, col);
  }

  /**
   * Displays the current state of the grid.
   */
  private void displayGrid() {
    System.out.println(grid.render());
  }

  /**
   * Checks if the game is over by determining if the grid is full.
   *
   * @return {@code true} if the game is over, {@code false} otherwise
   */
  public boolean isGameOver() {
    return grid.isFull();
  }

  /**
   * Switches the turn between players.
   */
  public void switchTurn() {
    // Logic to switch the current player (e.g., toggle between players)
  }
}
