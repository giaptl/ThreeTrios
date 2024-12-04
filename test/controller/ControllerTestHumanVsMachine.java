package controller;


import org.junit.Before;
import org.junit.Test;

import model.ICard;
import model.MockThreeTriosModel;
import player.IPlayer;
import strategy.FlipMaximizerStrategy;
import view.MockGameView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test class for the controller for a game between a human player and a machine player.
 */
public class ControllerTestHumanVsMachine {
  private Controller controller1;
  private Controller controller2;
  private MockThreeTriosModel mockModel;
  private IPlayer redPlayer;
  private IPlayer bluePlayer;
  private MockGameView mockView1;
  private MockGameView mockView2;

  @Before
  public void setUp() {
    // This game will be played with a human vs a strategy.
    mockModel = new MockThreeTriosModel(true, new FlipMaximizerStrategy());
    redPlayer = mockModel.getRedPlayer(); // Human player
    bluePlayer = mockModel.getBluePlayer(); // Machine player
    mockView1 = new MockGameView();
    mockView2 = new MockGameView();
    controller1 = new Controller(mockModel, redPlayer, mockView1);
    controller2 = new Controller(mockModel, bluePlayer, mockView2);
    mockModel.startGameWithConfig(mockModel.getGrid());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerHandlesNullModel() {
    new Controller(null, redPlayer, mockView1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerHandlesNullPlayer() {
    new Controller(mockModel, null, mockView1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerHandlesNullView() {
    new Controller(mockModel, redPlayer, null);
  }


  /**
   * Tests the progression of a game between a human player and a machine player.
   */
  @Test
  public void testGameProgressionHumanVsMachine() {
    // Round 1: Human player turn
    ICard card1 = mockModel.getPlayerHand(redPlayer).get(0);
    controller1.onCardSelected(redPlayer, 0);
    controller1.onGridCellSelected(0, 0);
    controller1.handleHumanPlayerTurn(0, 0);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card1, 0, 0));
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(card1));
    // Verify that the view was updated
    assertEquals(card1, mockView1.getLastUpdatedCard());
    assertEquals(0, mockView1.getLastUpdatedRow());
    assertEquals(0, mockView1.getLastUpdatedCol());
    // Verify ownership
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    System.out.println("Round 1:\n" + mockModel.getGrid());

    // Round 2: Machine player turn
    ICard card2 = mockModel.getPlayerHand(bluePlayer).get(0);
    controller2.onCardSelected(bluePlayer, 0);
    controller2.onGridCellSelected(0, 1);
    controller2.handleHumanPlayerTurn(0, 1);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card2, 0, 1));
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(card2));
    // Verify that the view was updated
    assertEquals(card2, mockView2.getLastUpdatedCard());
    assertEquals(0, mockView2.getLastUpdatedRow());
    assertEquals(1, mockView2.getLastUpdatedCol());
    // Verify ownership
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    System.out.println("Round 2:\n" + mockModel.getGrid());

    // Round 3: Human player turn
    ICard card3 = mockModel.getPlayerHand(redPlayer).get(0);
    controller1.onCardSelected(redPlayer, 0);
    controller1.onGridCellSelected(0, 2);
    controller1.handleHumanPlayerTurn(0, 2);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card3, 0, 2));
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(card3));
    // Verify that the view was updated
    assertEquals(card3, mockView1.getLastUpdatedCard());
    assertEquals(0, mockView1.getLastUpdatedRow());
    assertEquals(2, mockView1.getLastUpdatedCol());
    // Verify ownership
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    System.out.println("Round 3:\n" + mockModel.getGrid());

    // Round 4: Machine player turn
    ICard card4 = mockModel.getPlayerHand(bluePlayer).get(0);
    controller2.onCardSelected(bluePlayer, 0);
    controller2.onGridCellSelected(1, 2);
    controller2.handleHumanPlayerTurn(1, 2);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card4, 1, 2));
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(card4));
    // Verify that the view was updated
    assertEquals(card4, mockView2.getLastUpdatedCard());
    assertEquals(1, mockView2.getLastUpdatedRow());
    assertEquals(2, mockView2.getLastUpdatedCol());
    // Verify ownership
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 2).getOwner());
    System.out.println("Round 4:\n" + mockModel.getGrid());

    // Round 5: Human player turn
    ICard card5 = mockModel.getPlayerHand(redPlayer).get(0);
    controller1.onCardSelected(redPlayer, 0);
    controller1.onGridCellSelected(1, 1);
    controller1.handleHumanPlayerTurn(1, 1);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card5, 1, 1));
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(card5));
    // Verify that the view was updated
    assertEquals(card5, mockView1.getLastUpdatedCard());
    assertEquals(1, mockView1.getLastUpdatedRow());
    assertEquals(1, mockView1.getLastUpdatedCol());
    // Verify ownership
    assertEquals(redPlayer, mockModel.getGrid().getCell(1, 1).getOwner());
    System.out.println("Round 5:\n" + mockModel.getGrid());

    // Round 6: Machine player turn
    ICard card6 = mockModel.getPlayerHand(bluePlayer).get(0);
    controller2.onCardSelected(bluePlayer, 0);
    controller2.onGridCellSelected(1, 0);
    controller2.handleHumanPlayerTurn(1, 0);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card6, 1, 0));
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(card6));
    // Verify that the view was updated
    assertEquals(card6, mockView2.getLastUpdatedCard());
    assertEquals(1, mockView2.getLastUpdatedRow());
    assertEquals(0, mockView2.getLastUpdatedCol());
    // Verify ownership
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 0).getOwner());
    System.out.println("Round 6:\n" + mockModel.getGrid());

    // Round 7: Human player turn
    ICard card7 = mockModel.getPlayerHand(redPlayer).get(0);
    controller1.onCardSelected(redPlayer, 0);
    controller1.onGridCellSelected(2, 0);
    controller1.handleHumanPlayerTurn(2, 0);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card7, 2, 0));
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(card7));
    // Verify that the view was updated
    assertEquals(card7, mockView1.getLastUpdatedCard());
    assertEquals(2, mockView1.getLastUpdatedRow());
    assertEquals(0, mockView1.getLastUpdatedCol());
    // Verify ownership
    assertEquals(redPlayer, mockModel.getGrid().getCell(2, 0).getOwner());
    System.out.println("Round 7:\n" + mockModel.getGrid());

    // Round 8: Machine player turn
    ICard card8 = mockModel.getPlayerHand(bluePlayer).get(0);
    controller2.onCardSelected(bluePlayer, 0);
    controller2.onGridCellSelected(2, 1);
    controller2.handleHumanPlayerTurn(2, 1);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card8, 2, 1));
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(card8));
    // Verify that the view was updated
    assertEquals(card8, mockView2.getLastUpdatedCard());
    assertEquals(2, mockView2.getLastUpdatedRow());
    assertEquals(1, mockView2.getLastUpdatedCol());
    // Verify ownership
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 1).getOwner());
    System.out.println("Round 8:\n" + mockModel.getGrid());

    // Round 9: Human player turn
    ICard card9 = mockModel.getPlayerHand(redPlayer).get(0);
    controller1.onCardSelected(redPlayer, 0);
    controller1.onGridCellSelected(2, 2);
    controller1.handleHumanPlayerTurn(2, 2);
    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card9, 2, 2));
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(card9));
    // Verify that the view was updated
    assertEquals(card9, mockView1.getLastUpdatedCard());
    assertEquals(2, mockView1.getLastUpdatedRow());
    assertEquals(2, mockView1.getLastUpdatedCol());
    // Verify ownership
    assertEquals(redPlayer, mockModel.getGrid().getCell(2, 2).getOwner());
    System.out.println("Round 9:\n" + mockModel.getGrid());

    // Check game over
    assertTrue(mockModel.isGameOver());
    assertEquals(redPlayer, mockModel.getWinner());
  }

  @Test
  public void testCannotPickUpOrSelectFromOpponentsHand() {
    // Check that player 1 cannot select a card from player 2's hand
    controller1.onCardSelected(bluePlayer, 0);
    assertEquals("You cannot select cards from another player's hand.",
            mockView1.getLastErrorMessage());
  }

  @Test
  public void testPlayerMustSelectCardBeforePlacingOnBoard() {
    // Check that player 1 needs to select a card before selecting a grid cell
    controller1.onGridCellSelected(0, 0);
    assertEquals("Player " + redPlayer.getName() + ": You must select a card before "
            + "selecting a grid cell.", mockView1.getLastErrorMessage());

  }

}