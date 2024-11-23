package controller;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.Card;
import model.MockThreeTriosModel;
import player.HumanPlayer;
import player.IPlayer;
import view.IGameView;
import view.MockGameView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
/**
 * Test class for the controller. Yet to be implemented due to time constraints.
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
    player1 = new HumanPlayer("Bob", new ArrayList<>());
    player2 = new HumanPlayer("Sarah", new ArrayList<>());
    mockView1 = new MockGameView();
    mockView2 = new MockGameView();
    controller1 = new Controller(mockModel, player1, mockView1);
    controller2 = new Controller(mockModel, player2, mockView2);
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
  public void testGameProgression() {
    // Simulate a game turn for player1
    Card card1 = mockModel.getPlayerHand(player1).get(0);
    controller1.onCardSelected(player1, 0);
    controller1.onGridCellSelected(0, 0);

    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card1, 0, 0));
    assertFalse(mockModel.getPlayerHand(player1).contains(card1));

    // Verify that the view was updated
    assertEquals(card1, mockView1.getLastUpdatedCard());
    assertEquals(0, mockView1.getLastUpdatedRow());
    assertEquals(0, mockView1.getLastUpdatedCol());

    // Simulate a game turn for player2
    Card card2 = mockModel.getPlayerHand(player2).get(0);
    controller2.onCardSelected(player2, 0);
    controller2.onGridCellSelected(1, 1);

    // Verify that the card was played
    assertTrue(mockModel.isCardPlayed(card2, 1, 1));
    assertFalse(mockModel.getPlayerHand(player2).contains(card2));

    // Verify that the view was updated
    assertEquals(card2, mockView2.getLastUpdatedCard());
    assertEquals(1, mockView2.getLastUpdatedRow());
    assertEquals(1, mockView2.getLastUpdatedCol());
  }


//  @Test
//  public void testOnCardSelected() {
//    mockModel.setCurrentPlayer(player);
//    mockModel.addCardToPlayerHand(player, testCard);
//
//    controller.onCardSelected(player, 0);
//
//    assertEquals(player, mockView.getLastUpdatedPlayer());
//    assertEquals(testCard, mockView.getLastUpdatedCard());
//  }

//  @Test
//  public void testOnGridCellSelected() {
//    mockModel.setCurrentPlayer(player1);
//
//    controller1.onCardSelected(player1, 0);
//    controller1.onGridCellSelected(0, 0);
//
//    assertTrue(mockModel.isCardPlayed(testCard, 0, 0));
//    assertNull(mockView1.getLastUpdatedCard());
//  }
//
//  @Test
//  public void testGameOver() {
//    mockModel.setGameOver(true);
//    mockModel.setWinner(player);
//    mockModel.setPlayerScore(player, 10);
//
//    controller.gameOver(player);
//
//    assertEquals(player, view.getLastWinner());
//    assertEquals(10, view.getLastWinningScore());
//  }


}