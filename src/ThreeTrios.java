import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import extraFeatures.BattleRuleStrategy;
import extraFeatures.CombinedBattleRule;
import extraFeatures.FallenAceBattleRule;
import extraFeatures.PlusBattleRule;
import extraFeatures.SameBattleRule;
import finalProviderCode.view.GameGUI;
import finalProviderCode.view.ThreeTriosView;
import model.GameModel;
import model.Grid;
import configuration.ConfigurationReader;
import model.ICard;
import extraFeatures.NormalBattleRule;
import extraFeatures.ReverseBattleRule;
import adapters.ModelAdapter;
import model.ThreeTriosModel;
import player.HumanPlayer;
import player.IPlayer;
import player.MachinePlayer;
import strategy.CornerStrategy;
import strategy.FlipMaximizerStrategy;
import strategy.LeastLikelyFlippedStrategy;
import view.GameView;
import controller.Controller;
import view.IGameView;
import adapters.ViewAdapter;

/**
 * Main class to run the game from.
 */
public final class ThreeTrios {

  /**
   * Main class to run the game from.
   * Command line options:
   * - Player types: human, flipMaximizer, corner, LeastLikelyFlipped
   * - Battle rules: +reverse, +fallenace, +same, +plus
   * - View options: --provider-view (uses provider's view for Player 2)
   * Examples:
   * - Normal game: java ThreeTrios human human
   * - With provider view: java ThreeTrios human human --provider-view
   * - With battle rules: java ThreeTrios human human +reverse +fallenace
   * - Combined: java ThreeTrios human human --provider-view +same
   */
  public static void main(String[] args) {

    // Check if the correct number of command-line arguments is provided
    if (args.length < 2) {
      System.err.println("Usage: java ThreeTrios <player1> <player2>");
      System.err.println("Player types: human, flipMaximizer, corner, LeastLikelyFlipped");
      System.exit(1);
    }

    // Extract player types
    String player1Type = args[0];
    String player2Type = args[1];

    // Extract battle rule strategies & Check if the provider view should be used for player 2
    boolean useProviderViewForPlayer2 = false;
    List<BattleRuleStrategy> strategies = new ArrayList<>();
    for (String arg : args) {
      if (arg.equals("--use-provider-view")) {
        useProviderViewForPlayer2 = true;
      }
      switch (arg.toLowerCase()) {
        case "+reverse":
          strategies.add(new ReverseBattleRule());
          break;
        case "+fallenace":
          strategies.add(new FallenAceBattleRule());
          break;
        case "+same":
          strategies.add(new SameBattleRule());
          break;
        case "+plus":
          strategies.add(new PlusBattleRule());
          break;
      }
    }

    // Handle strategy creation
    if (strategies.isEmpty()) {
      strategies.add(new NormalBattleRule());
    }

    BattleRuleStrategy battleRuleStrategy;
    if (strategies.size() > 1) {
      battleRuleStrategy = new CombinedBattleRule(strategies);
    } else {
      battleRuleStrategy = strategies.get(0);
    }


    try {
      // Read the grid configuration
      String gridConfigPath = "src" + File.separator + "configuration"
              + File.separator + "configFiles"
              + File.separator + "board2WithReachableCells.config";
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
      ThreeTriosModel gameModel = new GameModel(battleRuleStrategy);
      gameModel.startGameWithConfig(grid, cards, false, player1, player2);
      System.out.println("Game started successfully.");


      // Launch the GUI on the Swing event dispatch thread
      boolean useProviderView = useProviderViewForPlayer2;
      SwingUtilities.invokeLater(() -> {
        // Player 1 always uses your view implementation
        GameView gameView1 = new GameView(gameModel);

        // Player 2 can use either your view or the provider's view
        IGameView gameView2;
        if (useProviderView) {
          ThreeTriosView providerView = new GameGUI(new ModelAdapter(gameModel, player1, player2));
          gameView2 = new ViewAdapter(providerView, gameModel);
        } else {
          gameView2 = new GameView(gameModel);
        }

        // Create and set up controllers
        Controller controller1 = new Controller(gameModel, player1, gameView1);
        Controller controller2 = new Controller(gameModel, player2, gameView2);

        // Set controllers
        (gameView1).setController(controller1);
        (gameView2).setController(controller2);

        // Set titles
        String view2Type = useProviderView ? "(Provider View)" : "";
        gameView1.setTitle("ThreeTrios - Player 1: " + player1.getName());
        gameView1.setVisible(true);
        if (gameView2 instanceof GameView) {
          ((GameView) gameView2).setTitle("ThreeTrios - Player 2: " + player2.getName() + " " + view2Type);
          ((GameView) gameView2).setVisible(true);
        } else if (gameView2 instanceof ViewAdapter) {
          ((ViewAdapter) gameView2).getProviderView().setTitle("ThreeTrios - Player 2: " + player2.getName() + " " + view2Type);
          ((ViewAdapter) gameView2).getProviderView().setVisible(true);
        }
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