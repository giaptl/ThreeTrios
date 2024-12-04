package view;

import model.ThreeTriosModel;

/**
 * The {@code TextView} class provides a way to display the state of the game
 * and other relevant information to the console or user interface.
 */
public class TextView {
  private final ThreeTriosModel model;

  /**
   * Constructs a {@code TextView} with a reference to the game model.
   *
   * @param model the {@code ThreeTriosModel} to be used for rendering the game state
   */
  public TextView(ThreeTriosModel model) {
    this.model = model;
  }

  /**
   * Displays the current state of the game grid.
   */
  public void displayGrid() {
    System.out.println(model.getGridView());
  }

  /**
   * Displays a custom message to the console.
   *
   * @param message the message to display
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * Displays the winner of the game or indicates if there is a tie.
   */
  public void displayWinner() {
    if (model.isGameOver()) {
      var winner = model.getWinner();
      if (winner != null) {
        displayMessage("The winner is: " + winner.getColor());
      } else {
        displayMessage("The game is a tie!");
      }
    }
  }
}
