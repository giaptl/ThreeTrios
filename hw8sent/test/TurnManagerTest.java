import model.managers.TurnManager;
import players.Player;
import players.PlayerImpl;
import org.junit.Test;
import static org.junit.Assert.assertEquals;




/**
 * Tests for the TurnManager class to ensure correct turn handling between players.
 */
public class TurnManagerTest {

  /**
   * Tests that the initial player is correctly set to the red player.
   */
  @Test
  public void testInitialPlayerIsRed() {
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    TurnManager turnManager = new TurnManager(redPlayer, bluePlayer);
    assertEquals(redPlayer, turnManager.getCurrentPlayer());
  }

  /**
   * Tests that the turn switches correctly to the other player.
   */
  @Test
  public void testTurnSwitching() {
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    TurnManager turnManager = new TurnManager(redPlayer, bluePlayer);
    turnManager.switchTurn();
    assertEquals(bluePlayer, turnManager.getCurrentPlayer());
  }

  /**
   * Tests multiple turn switches to verify it cycles back to the initial player.
   */
  @Test
  public void testMultipleTurnSwitches() {
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    TurnManager turnManager = new TurnManager(redPlayer, bluePlayer);
    turnManager.switchTurn();
    turnManager.switchTurn();
    assertEquals(redPlayer, turnManager.getCurrentPlayer());
  }
}
