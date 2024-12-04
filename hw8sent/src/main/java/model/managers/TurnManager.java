package model.managers;

import players.Player;

/**
 * The {@code TurnManager} class is responsible for managing player turns
 * during the game. It keeps track of the current player and switches turns
 * between two players.
 */
public class TurnManager {
  private Player currentPlayer;
  private final Player redPlayer;
  private final Player bluePlayer;

  /**
   * Constructs a {@code TurnManager} with the given players.
   * The red player is set as the starting player by default.
   *
   * @param redPlayer the red player
   * @param bluePlayer the blue player
   */
  public TurnManager(Player redPlayer, Player bluePlayer) {
    this.redPlayer = redPlayer;
    this.bluePlayer = bluePlayer;
    this.currentPlayer = redPlayer;
  }

  /**
   * Returns the player whose turn it currently is.
   *
   * @return the current player
   */
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Switches the turn to the next player. If the current player is the red player,
   * the turn switches to the blue player, and vice versa.
   */
  public void switchTurn() {
    currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
  }
}
