package controller;

import model.Player;
import model.ReadOnlyThreeTriosModel;
import strategy.FlipMaximizerStrategy;
import strategy.Move;
import strategy.Strategy;

/**
 * Game Setup and Flow:
 * 1. Load grid configuration from file
 * 2. Load card configuration from file
 * 3. Initialize game with grid and cards
 * 4. Distribute cards to players
 * 5. Red player starts the game
 * 6. Players take turns:
 *    a. Place a card on an empty cell
 *    b. Automatic battle phase for the placed card
 *    c. Switch to the other player
 * 7. Game ends when all card cells are filled
 * 8. Winner is determined by the most cards on the grid and in hand
 */
public class Controller {

  private final ReadOnlyThreeTriosModel model;

  public Controller(ReadOnlyThreeTriosModel model) {
    this.model = model;
  }

  public void playComputerTurn(Player computerPlayer) {
    Strategy strategy = new FlipMaximizerStrategy(); // Or CornerStrategy or another strategy

    Move bestMove = strategy.selectMove(computerPlayer, model);

    if (bestMove != null) {
      model.playCard(computerPlayer, bestMove.getCard(), bestMove.getRow(), bestMove.getCol());
      System.out.println("Computer played " + bestMove.getCard().getName() + " at (" + bestMove.getRow() + ", " + bestMove.getCol() + ")");

      // Update view after computer plays its turn...
      // view.refreshView();
    } else {
      System.out.println("No valid moves left for computer.");
      // Handle case where no valid moves are available...
    }
  }

}
