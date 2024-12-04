import model.ThreeTriosModelImpl;
import model.cards.Card;
import model.grid.Grid;
import players.Player;
import players.PlayerImpl;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * Tests for the ThreeTriosModel class to ensure game logic and state handling.
 */
public class ThreeTriosModelTest {

  /**
   * Tests initialization of the ThreeTriosModel with grid and players.
   */
  @Test
  public void testModelInitialization() {
    Grid grid = new Grid(3, 3);
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    ThreeTriosModelImpl model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);
    assertNotNull(model);
  }


  /**
   * Tests placing a card on the grid via the model.
   */
  @Test
  public void testPlaceCard() {
    Grid grid = new Grid(3, 3);
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");

    // Pass the correct arguments to the constructor
    ThreeTriosModelImpl model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);

    Card card = new Card("FireDragon", 8, 3, 6, 2);
    assertTrue(model.placeCard(redPlayer, card, 1, 1));
  }

}
