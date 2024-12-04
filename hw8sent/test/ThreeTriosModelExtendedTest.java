import model.ThreeTriosModel;
import model.ThreeTriosModelImpl;
import model.grid.Grid;
import model.cards.Card;
import players.Player;
import players.PlayerImpl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for the extended functionality of the {@code ThreeTriosModel} class.
 */
public class ThreeTriosModelExtendedTest {

  @Test
  public void testModelPlaceCard() {
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    Grid grid = new Grid(3, 3);
    ThreeTriosModel model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);

    Card card = new Card("FireDragon", 8, 3, 6, 2);
    assertTrue(model.placeCard(redPlayer, card, 1, 1));
    assertEquals(card, grid.getCardAt(1, 1));
  }

  @Test
  public void testGameOverDetection() {
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    Grid grid = new Grid(2, 2);
    ThreeTriosModel model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);

    Card card = new Card("SkyWhale", 4, 3, 6, 7);
    model.placeCard(redPlayer, card, 0, 0);
    model.placeCard(bluePlayer, card, 0, 1);
    model.placeCard(redPlayer, card, 1, 0);
    model.placeCard(bluePlayer, card, 1, 1);

    assertTrue(model.isGameOver());
  }
}
