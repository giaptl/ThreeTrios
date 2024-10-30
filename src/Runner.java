import java.io.File;
import java.io.IOException;
import java.util.List;
import model.Card;
import model.GameModel;
import model.Grid;
import configuration.ConfigurationReader;
import model.Player;
import view.TextView;

/**
 * Main class to run the game from.
 */
public class Runner {

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

      // Create a new GameModel instance and start game
      GameModel gameModel = new GameModel();
      gameModel.startGameWithConfig(grid, cards, true);
      System.out.println("Game started successfully.");

      // Create a new TextView instance (Testing)
      TextView textView = new TextView();
      textView.renderGrid(grid);
      Player currentPlayer = gameModel.getCurrentPlayer();
      textView.renderPlayerHand(currentPlayer, currentPlayer.getHand());

      // Simulate a move (Testing)
      int cardIndex = 0; // Select the first card in the hand
      int row = 0; // Select the row to place the card
      int col = 0; // Select the column to place the card

      // Play the card
      Card card = currentPlayer.getHand().get(cardIndex);
      gameModel.playCard(currentPlayer, card, row, col);

      // Start the battle phase
      gameModel.startBattlePhase(row, col);

      // Render the grid and the current player's hand again
      textView.renderGrid(grid);
      textView.renderPlayerHand(currentPlayer, currentPlayer.getHand());

    } catch (IOException e) {
      System.err.println("Error reading configuration files: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Invalid configuration: " + e.getMessage());
    }
  }
}
