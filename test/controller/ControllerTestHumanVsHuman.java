package controller;


import org.junit.Before;
import org.junit.Test;


import model.Card;
import model.MockThreeTriosModel;
import player.IPlayer;
import view.MockGameView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Test class for the controller for a game between two human players.
 */
public class ControllerTestHumanVsHuman {
  private Controller controller1;
  private Controller controller2;
  private MockThreeTriosModel mockModel;
  private IPlayer player1;
  private IPlayer player2;
  private MockGameView mockView1;
  private MockGameView mockView2;

  @Before
  public void setUp() {
    mockModel = new MockThreeTriosModel();
    player1 = mockModel.getRedPlayer();
    player2 = mockModel.getBluePlayer();
    mockView1 = new MockGameView();
    mockView2 = new MockGameView();
    controller1 = new Controller(mockModel, player1, mockView1);
    controller2 = new Controller(mockModel, player2, mockView2);
    mockModel.startGameWithConfig(mockModel.getGrid());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerHandlesNullModel() {
    new Controller(null, player1, mockView1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerHandlesNullPlayer() {
    new Controller(mockModel, null, mockView1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerHandlesNullView() {
    new Controller(mockModel, player1, null);
  }

  @Test
  public void testErrorBoxAppeared() {
    // Attempt to play a card without selecting one first
    controller1.onGridCellSelected(0, 0);

    // Verify that an error message was shown
    assertEquals("Player " + player1.getName() + ": You must select a card before "
            + "selecting a grid cell.", mockView1.getLastErrorMessage());
  }

  @Test
  public void testInvalidMoveToHole() {
    // Set a cell as a hole
    mockModel.setCellAsHole(0, 0);

    // Attempt to play a card to the hole
    controller1.onCardSelected(player1, 0);
    controller1.onGridCellSelected(0, 0);

    // Verify that an error message was shown
    assertEquals("Invalid move: Card cannot be placed on a hole.", mockView1.getLastErrorMessage());
  }

  @Test
  public void testCardDeselection() {
    // Select a card
    controller1.onCardSelected(player1, 0);
    Card selectedCard = mockModel.getPlayerHand(player1).get(0);
    assertEquals(selectedCard, mockView1.getLastUpdatedCard());

    // Deselect the same card
    controller1.onCardSelected(player1, 0);
    assertNull(mockView1.getLastUpdatedCard());
  }

  // In ControllerTestHumanVsHuman.java
  @Test
  public void testViewRefresh() {
    // Select a card and play it
    controller1.onCardSelected(player1, 0);
    controller1.onGridCellSelected(0, 0);

    // Verify that the view was refreshed
    assertTrue(mockView1.isViewRefreshed());

    // Reset the view refresh flag
    mockView1.resetViewRefreshed();

    // Select another card and play it
    controller2.onCardSelected(player2, 0);
    controller2.onGridCellSelected(1, 1);

    // Verify that the view was refreshed again
    assertTrue(mockView2.isViewRefreshed());
  }

  @Test
  public void testGameProgression() {

    // Round 1: Human player 1 turn
    Card card1 = mockModel.getPlayerHand(player1).get(0);

    // Check that player 1 needs to select a card before selecting a grid cell
    controller1.onGridCellSelected(0, 0);
    assertEquals("Player " + player1.getName() + ": You must select a card before "
            + "selecting a grid cell.", mockView1.getLastErrorMessage());

    // Check that player 1 cannot select a card from player 2's hand
    controller1.onCardSelected(player2, 0);
    assertEquals("You cannot select cards from another player's hand.",
            mockView1.getLastErrorMessage());

    // Check that player 2 cannot play to their board during player 1's turn
    controller2.onGridCellSelected(0, 0);
    assertEquals("It's not your turn.", mockView2.getLastErrorMessage());
    controller2.onCardSelected(player1, 0);
    assertEquals("It's not your turn.", mockView2.getLastErrorMessage());
    controller2.onCardSelected(player2, 0);
    assertEquals("It's not your turn.", mockView2.getLastErrorMessage());

    controller1.onCardSelected(player1, 0);
    controller1.onGridCellSelected(0, 0);
    controller1.handleHumanPlayerTurn(0, 0);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card1, 0, 0));
    assertFalse(mockModel.getPlayerHand(player1).contains(card1));
    // Verify that the view was updated
    assertEquals(card1, mockView1.getLastUpdatedCard());
    assertEquals(0, mockView1.getLastUpdatedRow());
    assertEquals(0, mockView1.getLastUpdatedCol());
    // Verify ownership
    assertEquals(player1, mockModel.getGrid().getCell(0, 0).getOwner());

    // Check that player 1 cannot still play on their board
    controller1.onGridCellSelected(0, 1);
    assertEquals("It's not your turn.", mockView1.getLastErrorMessage());

    // Check that player 2 cannot play on player 1's board
    // Should be null since player 2 should not be able to click on their hand on player 1's board
    controller1.handleHumanPlayerTurn(0, 0);
    assertEquals("It's not your turn.", mockView2.getLastErrorMessage());

    System.out.println("Round 1:\n" + mockModel.getGrid());


    // Round 2: Human player 2 turn
    Card card2 = mockModel.getPlayerHand(player2).get(0);

    // Check that multiple cards can be selected from the hand
    controller2.onCardSelected(player2, 0);
    controller2.onCardSelected(player2, 1);
    controller2.onCardSelected(player2, 2);
    controller2.onCardSelected(player2, 0);

    // Check that player 2 cannot play a card to a cell that is already occupied
    controller2.onGridCellSelected(0, 0);
    assertEquals("Invalid move: Cell is not empty.", mockView2.getLastErrorMessage());

    controller2.onGridCellSelected(1, 1);
    controller1.handleHumanPlayerTurn(1, 1);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card2, 1, 1));
    assertFalse(mockModel.getPlayerHand(player2).contains(card2));
    // Verify that the view was updated
    assertEquals(card2, mockView2.getLastUpdatedCard());
    assertEquals(1, mockView2.getLastUpdatedRow());
    assertEquals(1, mockView2.getLastUpdatedCol());
    // Verify ownership
    assertEquals(player1, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(player2, mockModel.getGrid().getCell(1, 1).getOwner());
    System.out.println("Round 2:\n" + mockModel.getGrid());

    // Round 3: Human player 1 turn
    Card card3 = mockModel.getPlayerHand(player1).get(0);
    controller1.onCardSelected(player1, 0);
    controller1.onGridCellSelected(1, 0);
    controller1.handleHumanPlayerTurn(1, 0);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card3, 1, 0));
    assertFalse(mockModel.getPlayerHand(player1).contains(card3));
    // Verify that the view was updated
    assertEquals(card3, mockView1.getLastUpdatedCard());
    assertEquals(1, mockView1.getLastUpdatedRow());
    assertEquals(0, mockView1.getLastUpdatedCol());
    // Verify ownership
    assertEquals(player1, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(player1, mockModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(player1, mockModel.getGrid().getCell(1, 0).getOwner());
    System.out.println("Round 3:\n" + mockModel.getGrid());

    // Round 4: Human player 2 turn
    Card card4 = mockModel.getPlayerHand(player2).get(2);
    controller2.onCardSelected(player2, 2);
    controller2.onGridCellSelected(2, 2);
    controller1.handleHumanPlayerTurn(2, 2);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card4, 2, 2));
    assertFalse(mockModel.getPlayerHand(player2).contains(card4));
    // Verify that the view was updated
    assertEquals(card4, mockView2.getLastUpdatedCard());
    assertEquals(2, mockView2.getLastUpdatedRow());
    assertEquals(2, mockView2.getLastUpdatedCol());
    // Verify ownership
    assertEquals(player1, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(player1, mockModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(player1, mockModel.getGrid().getCell(1, 0).getOwner());
    assertEquals(player2, mockModel.getGrid().getCell(2, 2).getOwner());
    System.out.println("Round 4:\n" + mockModel.getGrid());

    // Round 5: Human player 1 turn
    Card card5 = mockModel.getPlayerHand(player1).get(0);
    controller1.onCardSelected(player1, 0);
    controller1.onGridCellSelected(0, 1);
    controller1.handleHumanPlayerTurn(0, 1);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card5, 0, 1));
    assertFalse(mockModel.getPlayerHand(player1).contains(card5));
    // Verify that the view was updated
    assertEquals(card5, mockView1.getLastUpdatedCard());
    assertEquals(0, mockView1.getLastUpdatedRow());
    assertEquals(1, mockView1.getLastUpdatedCol());
    System.out.println("Round 5:\n" + mockModel.getGrid());

    // Round 6: Human player 2 turn
    Card card6 = mockModel.getPlayerHand(player2).get(0);
    controller2.onCardSelected(player2, 0);
    controller2.onGridCellSelected(0, 2);
    controller1.handleHumanPlayerTurn(0, 2);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card6, 0, 2));
    assertFalse(mockModel.getPlayerHand(player2).contains(card6));
    // Verify that the view was updated
    assertEquals(card6, mockView2.getLastUpdatedCard());
    assertEquals(0, mockView2.getLastUpdatedRow());
    assertEquals(2, mockView2.getLastUpdatedCol());
    System.out.println("Round 6:\n" + mockModel.getGrid());

    // Round 7: Human player 1 turn
    Card card7 = mockModel.getPlayerHand(player1).get(0);
    controller1.onCardSelected(player1, 0);
    controller1.onGridCellSelected(2, 0);
    controller1.handleHumanPlayerTurn(2, 0);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card7, 2, 0));
    assertFalse(mockModel.getPlayerHand(player1).contains(card7));
    // Verify that the view was updated
    assertEquals(card7, mockView1.getLastUpdatedCard());
    assertEquals(2, mockView1.getLastUpdatedRow());
    assertEquals(0, mockView1.getLastUpdatedCol());
    System.out.println("Round 7:\n" + mockModel.getGrid());

    // Round 8: Human player 2 turn
    Card card8 = mockModel.getPlayerHand(player2).get(0);
    controller2.onCardSelected(player2, 0);
    controller2.onGridCellSelected(2, 1);
    controller1.handleHumanPlayerTurn(2, 1);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card8, 2, 1));
    assertFalse(mockModel.getPlayerHand(player2).contains(card8));
    // Verify that the view was updated
    assertEquals(card8, mockView2.getLastUpdatedCard());
    assertEquals(2, mockView2.getLastUpdatedRow());
    assertEquals(1, mockView2.getLastUpdatedCol());
    System.out.println("Round 8:\n" + mockModel.getGrid());

    // Round 9: Human player 1 turn
    Card card9 = mockModel.getPlayerHand(player1).get(0);
    controller1.onCardSelected(player1, 0);
    controller1.onGridCellSelected(1, 2);
    controller1.handleHumanPlayerTurn(1, 2);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card9, 1, 2));
    assertFalse(mockModel.getPlayerHand(player1).contains(card9));
    // Verify that the view was updated
    assertEquals(card9, mockView1.getLastUpdatedCard());
    assertEquals(1, mockView1.getLastUpdatedRow());
    assertEquals(2, mockView1.getLastUpdatedCol());
    System.out.println("Round 9:\n" + mockModel.getGrid());

    // Check game over
    assertTrue(mockModel.isGameOver());
    assertEquals(player1, mockModel.getWinner());
    assertEquals(9, mockModel.getPlayerScore(player1));
    assertEquals(0, mockModel.getPlayerScore(player2));
    // Check gameOver popup was shown
    assertEquals(player1, mockView1.getGameOverWinner());
    assertEquals(9, mockView1.getGameOverScore());

    // We do not need to check if players can still play cards from their hand once the game is over
    // because the hand panel will automatically remove once the player hand is empty

  }

}