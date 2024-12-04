import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import model.Card;
import model.GameModel;
import model.Grid;
import configuration.ConfigurationReader;
import model.ICard;
import player.HumanPlayer;
import player.IPlayer;
import player.MachinePlayer;
import strategy.CornerStrategy;
import strategy.FlipMaximizerStrategy;
import strategy.LeastLikelyFlippedStrategy;
import view.GameView;
import controller.Controller;

/**
 * Main class to run the game from.
 */
public final class ThreeTrios {

  /**
   * Main method to run the game from.
   */
  public static void main(String[] args) {

    if (args.length != 2) {
      System.err.println("Usage: java ThreeTrios <player1> <player2>");
      System.err.println("Player types: human, flipMaximizer, corner, LeastLikelyFlipped");
      System.exit(1);
    }

    String player1Type = args[0];
    String player2Type = args[1];

    try {
      // Read the grid configuration
      String gridConfigPath = "src" + File.separator + "configuration"
              + File.separator + "configFiles"
              + File.separator + "board3WithSeparateGroupsOfCells.config";
      Grid grid = ConfigurationReader.readGridConfig(gridConfigPath);
      System.out.println("Grid loaded successfully with " + grid.getRows() + " rows and "
              + grid.getColumns() + " columns.");

      // Read the card data
      String cardDataPath = "src" + File.separator + "configuration"
              + File.separator + "configFiles"
              + File.separator + "cardsEnoughForAllBoards.config";
      List<ICard> cards = ConfigurationReader.readCardData(cardDataPath);
      System.out.println("Card data loaded successfully with " + cards.size() + " cards.");


      // Create players based on command-line arguments
      IPlayer player1 = createPlayer(player1Type);
      IPlayer player2 = createPlayer(player2Type);

      // Create a new GameModel instance and start the game
      GameModel gameModel = new GameModel();
      gameModel.startGameWithConfig(grid, cards, false, player1, player2);
      System.out.println("Game started successfully.");


      // Launch the GUI on the Swing event dispatch thread
      SwingUtilities.invokeLater(() -> {
        // Create views for both players
        GameView gameView1 = new GameView(gameModel);
        GameView gameView2 = new GameView(gameModel);

        // Create controllers for both players
        Controller controller1 = new Controller(gameModel, player1, gameView1);
        Controller controller2 = new Controller(gameModel, player2, gameView2);

        // Set controllers for each view
        gameView1.setController(controller1);
        gameView2.setController(controller2);

        gameView1.setTitle("ThreeTrios Game - Player 1: " + player1.getName());
        gameView2.setTitle("ThreeTrios Game - Player 2: " + player2.getName());

        // Make both views visible
        gameView1.setVisible(true);
        gameView2.setVisible(true);
      });
      System.out.print("GUI launched successfully.");

    } catch (IOException e) {
      System.err.println("Error reading configuration files: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Invalid configuration: " + e.getMessage());
    }
  }

  private static IPlayer createPlayer(String playerType) {
    List<ICard> hand = new ArrayList<>();
    switch (playerType.toLowerCase()) {
      case "human":
        return new HumanPlayer("Human", hand);
      case "flipmaximizer":
        return new MachinePlayer("Machine", hand, new FlipMaximizerStrategy());
      case "corner":
        return new MachinePlayer("Machine", hand, new CornerStrategy());
      case "leastlikelyflipped":
        return new MachinePlayer("Machine", hand, new LeastLikelyFlippedStrategy());
      default:
        throw new IllegalArgumentException("Unknown player type: " + playerType);
    }
  }
}