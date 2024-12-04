import model.cards.Card;
import model.grid.Grid;
import model.managers.GameManager;
import model.utils.Hand;
import org.junit.Before;
import org.junit.Test;
import players.Player;
import players.PlayerImpl;
import view.GameGUI;
import view.SidePanel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;


/**
 * Additional test class to provide more comprehensive coverage of key components.
 */
public class AdditionalTests {

  private Player redPlayer;
  private Player bluePlayer;
  private Grid grid;
  private GameManager gameManager;
  private Hand hand;

  @Before
  public void setUp() {
    redPlayer = new PlayerImpl("RED");
    bluePlayer = new PlayerImpl("BLUE");
    grid = new Grid(3, 3);
    gameManager = new GameManager(grid, redPlayer, bluePlayer);
    hand = new Hand();
  }

  @Test
  public void testPlaceCardAtTopLeftCorner() {
    Card card = new Card("HeroKnight", 4, 5, 7, 3);
    assertTrue(grid.placeCard(0, 0, card, "RED"));
  }

  @Test
  public void testPlaceCardAtBottomRightCorner() {
    Card card = new Card("SkyWhale", 2, 4, 6, 8);
    assertTrue(grid.placeCard(2, 2, card, "BLUE"));
  }

  // Player Interaction Tests
  @Test
  public void testAddAndRemoveCardFromPlayerHand() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    redPlayer.addCard(card);
    assertEquals(1, redPlayer.getHand().size());

    redPlayer.removeCard(card);
    assertTrue(redPlayer.getHand().isEmpty());
  }

  // Hand Functionality Tests
  @Test
  public void testHandAddMultipleCards() {
    hand.addCard(new Card("FireDragon", 8, 3, 6, 2));
    hand.addCard(new Card("IceGiant", 4, 5, 7, 3));
    assertEquals(2, hand.getCards().size());
  }

  @Test
  public void testHandRemoveCardNotPresent() {
    hand.addCard(new Card("SkyWhale", 2, 4, 6, 8));
    hand.removeCard(new Card("HeroKnight", 4, 5, 7, 3));
    assertEquals(1, hand.getCards().size());
  }

  // Game Manager Tests
  @Test
  public void testGameOverWithEmptyGrid() {
    assertFalse(gameManager.isGameOver());
  }

  @Test
  public void testGameOverWithFullGrid() {
    Card card = new Card("SkyWhale", 2, 4, 6, 8);
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        grid.placeCard(r, c, card, (r + c) % 2 == 0 ? "RED" : "BLUE");
      }
    }
    assertTrue(gameManager.isGameOver());
  }

  // GUI Panel Tests
  @Test
  public void testSidePanelRendersCards() {
    GameGUI gui = new GameGUI(new model.ThreeTriosModelImpl(grid, redPlayer, bluePlayer));
    SidePanel leftPanel = (SidePanel) gui.getLeftPanel();
    assertNotNull(leftPanel);
    assertEquals(0, leftPanel.getComponentCount());

    redPlayer.addCard(new Card("IceGiant", 4, 5, 7, 3));
    gui.repaint(); // Ensure UI reflects card addition
    assertEquals(1, leftPanel.getComponentCount());
  }

  // Ensure the refresh method correctly updates the grid
  @Test
  public void testGridRefreshAfterPlacement() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    grid.placeCard(1, 1, card, "RED");
    assertEquals("R", grid.render().charAt(4));
  }
}
