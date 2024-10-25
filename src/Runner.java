import java.io.File;
import java.io.IOException;
import java.util.List;
import model.Card;
import model.Grid;
import configuration.ConfigurationReader;

public class Runner {

  public static void main(String[] args) {
    try {
      // Define the paths to the configuration files
      String gridConfigPath = "configFiles" + File.separator + "board.config";
      String cardDataPath = "configFiles" + File.separator + "cards.config";

      // Read the grid configuration
      Grid grid = ConfigurationReader.readGridConfig(gridConfigPath);
      System.out.println("Grid loaded successfully with " + grid.getRows() + " rows and " +
              grid.getColumns() + " columns.");

      // Read the card data
      List<Card> cards = ConfigurationReader.readCardData(cardDataPath);
      System.out.println("Card data loaded successfully with " + cards.size() + " cards.");

      // Additional logic to use the grid and cards can be added here

    } catch (IOException e) {
      System.err.println("Error reading configuration files: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Invalid configuration: " + e.getMessage());
    }
  }
}