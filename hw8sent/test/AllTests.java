import model.cards.Card;
import model.cards.CardLoader;
import model.grid.Grid;
import model.managers.GameManager;
import model.utils.Hand;
import players.Player;
import players.PlayerImpl;
import view.GameGUI;
import view.SidePanel;

import javax.swing.JPanel;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Comprehensive test class covering all major aspects of the game model, view, and controller.
 */
public class AllTests {

  private Player redPlayer;
  private Grid grid;
  private GameManager gameManager;
  private Hand hand;
  private GameGUI gui;

  @Before
  public void setUp() {
    redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE"); // Moved to a local variable
    grid = new Grid(3, 3);
    gameManager = new GameManager(grid, redPlayer, bluePlayer);
    hand = new Hand();
    gui = new GameGUI(new model.ThreeTriosModelImpl(grid, redPlayer, bluePlayer));
  }

  // CardLoader Tests
  @Test
  public void testLoadValidCardFile() {
    List<Card> cards = CardLoader.loadCards("src/main/resources/cards/cards_example.txt");
    assertNotNull(cards);
    assertFalse(cards.isEmpty());
  }

  @Test
  public void testLoadEmptyCardFile() {
    List<Card> cards = CardLoader.loadCards("src/main/resources/cards/empty_file.txt");
    assertTrue(cards.isEmpty());
  }

  // Card Tests
  @Test
  public void testCardInitialization() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    assertEquals("FireDragon", card.getName());
    assertEquals(8, card.getNorth());
    assertEquals(3, card.getSouth());
    assertEquals(6, card.getEast());
    assertEquals(2, card.getWest());
  }

  @Test
  public void testCardToString() {
    Card card = new Card("IceGiant", 2, 7, 5, 9);
    String expected = "IceGiant (N: 2, S: 7, E: 5, W: 9)";
    assertEquals(expected, card.toString());
  }

  @Test
  public void testCardEquality() {
    Card card1 = new Card("FireDragon", 8, 3, 6, 2);
    Card card2 = new Card("FireDragon", 8, 3, 6, 2);
    assertEquals(card1, card2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardInitialization() {
    new Card("InvalidCard", -1, -3, 10, 15); // Negative values should throw an exception
  }

  @Test
  public void testCardNameLength() {
    Card card = new Card("LongCardNameExample", 5, 5, 5, 5);
    assertEquals("LongCardNameExample", card.getName());
  }

  // Grid Tests
  @Test
  public void testGridInitialization() {
    assertNotNull(grid);
    assertEquals(3, grid.getRows());
    assertEquals(3, grid.getCols());
  }

  @Test
  public void testPlaceCardSuccess() {
    Card card = new Card("HeroKnight", 4, 5, 7, 3);
    assertTrue(grid.placeCard(1, 1, card, "RED"));
  }

  @Test
  public void testPlaceCardOnOccupiedCell() {
    Card card = new Card("HeroKnight", 4, 5, 7, 3);
    grid.placeCard(1, 1, card, "RED");
    assertFalse(grid.placeCard(1, 1, new Card("IceGiant", 2, 7, 5, 9), "BLUE"));
  }

  @Test
  public void testGridFullCondition() {
    Card card = new Card("SkyWhale", 4, 3, 6, 7);
    grid.placeCard(0, 0, card, "RED");
    grid.placeCard(0, 1, card, "BLUE");
    grid.placeCard(1, 0, card, "RED");
    grid.placeCard(1, 1, card, "BLUE");
    grid.placeCard(2, 2, card, "RED");
    assertTrue(grid.isFull());
  }

  @Test
  public void testGridIsNotFull() {
    Card card = new Card("SkyWhale", 4, 3, 6, 7);
    grid.placeCard(0, 0, card, "RED");
    assertFalse(grid.isFull());
  }

  @Test
  public void testGridCellOwnership() {
    Card card = new Card("HeroKnight", 4, 5, 7, 3);
    grid.placeCard(1, 1, card, "RED");
    assertEquals("RED", grid.getCellOwner(1, 1));
  }

  // Hand Tests
  @Test
  public void testHandInitialization() {
    assertTrue(hand.getCards().isEmpty());
  }

  @Test
  public void testAddCard() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    hand.addCard(card);
    assertEquals(1, hand.getCards().size());
    assertEquals(card, hand.getCards().get(0));
  }

  @Test
  public void testRemoveCard() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    hand.addCard(card);
    hand.removeCard(card);
    assertTrue(hand.getCards().isEmpty());
  }

  @Test
  public void testGetCardsReturnsCopy() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    hand.addCard(card);
    List<Card> cards = hand.getCards();
    cards.clear();
    assertFalse(hand.getCards().isEmpty());
  }

  // Player Tests
  @Test
  public void testPlayerInitialization() {
    assertEquals("RED", redPlayer.getColor());
    assertTrue(redPlayer.getHand().isEmpty());
  }

  @Test
  public void testAddCardToPlayerHand() {
    Card card = new Card("AngryDragon", 3, 8, 7, 2);
    redPlayer.addCard(card);
    assertEquals(1, redPlayer.getHand().size());
    assertEquals(card, redPlayer.getHand().get(0));
  }

  @Test
  public void testRemoveCardFromPlayerHand() {
    Card card = new Card("AngryDragon", 3, 8, 7, 2);
    redPlayer.addCard(card);
    redPlayer.removeCard(card);
    assertTrue(redPlayer.getHand().isEmpty());
  }

  @Test
  public void testPlayerHandSize() {
    redPlayer.addCard(new Card("HeroKnight", 4, 5, 7, 3));
    redPlayer.addCard(new Card("IceGiant", 2, 7, 5, 9));
    assertEquals(2, redPlayer.getHand().size());
  }

  @Test
  public void testPlayerName() {
    assertEquals("RED", redPlayer.getName());
  }

  // GameManager Tests
  @Test
  public void testGameOverCondition() {
    Card card = new Card("SkyWhale", 4, 3, 6, 7);
    gameManager.playTurn(card, 0, 0);
    gameManager.playTurn(card, 0, 1);
    gameManager.playTurn(card, 1, 0);
    gameManager.playTurn(card, 1, 1);
    assertTrue(gameManager.isGameOver());
  }

  // GameGUI Tests
  @Test
  public void testSidePanelInitializationInGUI() {
    SidePanel leftPanel = (SidePanel) gui.getLeftPanel();
    SidePanel rightPanel = (SidePanel) gui.getRightPanel();
    assertNotNull(leftPanel);
    assertNotNull(rightPanel);
  }

  @Test
  public void testGridPanelInitializationInGUI() {
    JPanel gridPanel = gui.getGridPanel();
    assertNotNull(gridPanel);
    assertEquals(9, gridPanel.getComponentCount()); // Assuming a 3x3 grid
  }

  // Comprehensive Test
  @Test
  public void testCompleteGameScenario() {
    Card card1 = new Card("FireDragon", 8, 3, 6, 2);
    Card card2 = new Card("IceGiant", 4, 5, 7, 3);
    gameManager.playTurn(card1, 0, 0);
    gameManager.playTurn(card2, 0, 1);
    assertTrue(grid.isOccupied(0, 0));
    assertTrue(grid.isOccupied(0, 1));
  }
}
