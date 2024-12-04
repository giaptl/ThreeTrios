import model.ThreeTriosModel;
import model.ThreeTriosModelImpl;
import model.cards.Card;
import model.cards.CardLoader;
import model.grid.Grid;
import model.managers.TurnManager;
import model.utils.Hand;
import players.Player;
import players.PlayerImpl;
import view.GameGUI;
import view.SidePanel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Consolidated test class to cover various aspects of the Three Trios game.
 */
public class ComprehensiveTest {

  private ThreeTriosModel model;
  private Player redPlayer;
  private Player bluePlayer;
  private GameGUI gui;
  private Hand hand;
  private Grid grid;

  @Before
  public void setUp() {
    grid = new Grid(3, 3);
    redPlayer = new PlayerImpl("RED");
    bluePlayer = new PlayerImpl("BLUE");
    model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);
    gui = new GameGUI(model);
    hand = new Hand();
  }

  // 1. Tests for GameGUI class
  @Test
  public void testGUIInitialization() {
    assertNotNull(gui);
  }

  @Test
  public void testGridInitializationInGUI() {
    JPanel gridPanel = gui.getGridPanel();
    assertNotNull(gridPanel);
    assertTrue(gridPanel.getComponentCount() > 0);
  }

  @Test
  public void testSidePanelInitializationInGUI() {
    JPanel leftPanel = gui.getLeftPanel();
    JPanel rightPanel = gui.getRightPanel();
    assertNotNull(leftPanel);
    assertNotNull(rightPanel);
    assertTrue(leftPanel.getComponentCount() > 0);
    assertTrue(rightPanel.getComponentCount() > 0);
  }

  @Test
  public void testCellHighlightingOnSelection() {
    JButton cellButton = new JButton();
    cellButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    cellButton.doClick();
    assertTrue(cellButton.isFocusOwner());
  }

  // 2. Tests for SidePanel class
  @Test
  public void testSidePanelInitialization() {
    SidePanel sidePanel = new SidePanel(redPlayer, Color.PINK);
    assertNotNull(sidePanel);
    assertEquals(0, sidePanel.getComponentCount()); // Initially empty
    redPlayer.addCard(new Card("FireDragon", 8, 3, 6, 2));
    sidePanel = new SidePanel(redPlayer, Color.PINK);
    assertEquals(1, sidePanel.getComponentCount()); // Now has one card
  }

  // 3. Tests for CardLoader class
  @Test
  public void testLoadValidCardFile() {
    List<Card> cards = CardLoader.loadCards("src/main/resources/cards/cards_example.txt");
    assertNotNull(cards);
    assertFalse(cards.isEmpty());
  }

  @Test
  public void testLoadEmptyCardFile() {
    List<Card> cards = CardLoader.loadCards("src/main/resources/cards/empty_file.txt");
    assertNotNull(cards);
    assertTrue(cards.isEmpty());
  }

  // 4. Tests for Card class
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
    Card card = new Card("IceGiant", 4, 5, 7, 3);
    assertEquals("IceGiant (N: 4, S: 5, E: 7, W: 3)", card.toString());
  }

  // 5. Tests for ThreeTriosModel class
  @Test
  public void testModelInitialization() {
    assertNotNull(model);
  }

  @Test
  public void testPlaceCardInModel() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    assertTrue(model.placeCard(redPlayer, card, 1, 1));
  }

  // 6. Tests for Hand class
  @Test
  public void testHandInitialization() {
    assertTrue(hand.getCards().isEmpty());
  }

  @Test
  public void testAddCardToHand() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    hand.addCard(card);
    assertEquals(1, hand.getCards().size());
    assertEquals(card, hand.getCards().get(0));
  }

  // 7. Tests for Grid class
  @Test
  public void testGridInitialization() {
    assertNotNull(grid);
  }

  @Test
  public void testGridIsEmptyInitially() {
    assertEquals("_ _ _ \n_ _ _ \n_ _ _ \n", grid.render());
  }

  @Test
  public void testPlaceCardOnGrid() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    assertTrue(grid.placeCard(1, 1, card, "RED"));
    assertEquals("R _ _ \n_ _ _ \n_ _ _ \n", grid.render());
  }

  // 8. Tests for TurnManager class
  @Test
  public void testInitialPlayerIsRed() {
    TurnManager turnManager = new TurnManager(redPlayer, bluePlayer);
    assertEquals(redPlayer, turnManager.getCurrentPlayer());
  }

  @Test
  public void testSwitchTurn() {
    TurnManager turnManager = new TurnManager(redPlayer, bluePlayer);
    turnManager.switchTurn();
    assertEquals(bluePlayer, turnManager.getCurrentPlayer());
  }

  @Test
  public void testMultipleTurnSwitches() {
    TurnManager turnManager = new TurnManager(redPlayer, bluePlayer);
    turnManager.switchTurn();
    turnManager.switchTurn();
    assertEquals(redPlayer, turnManager.getCurrentPlayer());
  }
}
