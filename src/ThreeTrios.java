import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import model.Card;
import model.GameModel;
import model.Grid;
import configuration.ConfigurationReader;
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
    try {
      // Read the grid configuration
      String gridConfigPath = "configFiles" + File.separator + "board.config";
      Grid grid = ConfigurationReader.readGridConfig(gridConfigPath);
      System.out.println("Grid loaded successfully with " + grid.getRows() + " rows and "
              + grid.getColumns() + " columns.");

      // Read the card data
      String cardDataPath = "configFiles" + File.separator + "cardsEnoughForAllBoards.config";
      List<Card> cards = ConfigurationReader.readCardData(cardDataPath);
      System.out.println("Card data loaded successfully with " + cards.size() + " cards.");

      // Create a new GameModel instance and start the game
      GameModel gameModel = new GameModel();
      gameModel.startGameWithConfig(grid, cards, true);
      System.out.println("Game started successfully.");

      // Launch the GUI on the Swing event dispatch thread
      SwingUtilities.invokeLater(() -> {
        GameView gameView = new GameView(gameModel);
        Controller controller = new Controller(gameModel, gameView);
        gameView.setController(controller);
        gameView.setVisible(true);
      });
      System.out.print("GUI launched successfully.");

    } catch (IOException e) {
      System.err.println("Error reading configuration files: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Invalid configuration: " + e.getMessage());
    }
  }
}