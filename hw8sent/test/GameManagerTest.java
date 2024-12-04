import model.cards.Card;
import model.grid.Grid;
import model.managers.GameManager;
import players.Player;
import players.PlayerImpl;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the GameManager class to verify game state and functionality.
 */
public class GameManagerTest {

  /**
   * Tests if the game ends correctly when the grid is full.
   */
  @Test
  public void testGameOverCondition() {
    Grid grid = new Grid(2, 2);
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    GameManager manager = new GameManager(grid, redPlayer, bluePlayer);
    Card card = new Card("SkyWhale", 4, 3, 6, 7);
    manager.playTurn(card, 0, 0);
    manager.playTurn(card, 0, 1);
    manager.playTurn(card, 1, 0);
    manager.playTurn(card, 1, 1);
    assertTrue(manager.isGameOver());
  }
}
