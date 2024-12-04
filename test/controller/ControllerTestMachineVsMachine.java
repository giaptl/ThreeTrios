package controller;

import org.junit.Before;
import org.junit.Test;

import model.Card;
import model.MockThreeTriosModel;
import player.IPlayer;
import strategy.CornerStrategy;
import view.MockGameView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test class for the controller for a game between two machine players.
 * We are going to give both machine players the corner strategy to make testing simpler.
 */
public class ControllerTestMachineVsMachine {
  private Controller controller1;
  private Controller controller2;
  private MockThreeTriosModel mockModel;
  private IPlayer player1;
  private IPlayer player2;
  private MockGameView mockView1;
  private MockGameView mockView2;

  @Before
  public void setUp() {
    mockModel = new MockThreeTriosModel(true,
            new CornerStrategy(), new CornerStrategy());
    player1 = mockModel.getRedPlayer();
    player2 = mockModel.getBluePlayer();
    mockView1 = new MockGameView();
    mockView2 = new MockGameView();
    controller1 = new Controller(mockModel, player1, mockView1);
    controller2 = new Controller(mockModel, player2, mockView2);
    mockModel.startGameWithConfig(mockModel.getGrid());
  }

  @Test
  public void testGameProgression() {
    // Round 1: Machine player 1 turn
    controller1.handleMachinePlayerTurn();
    verifyMove(0, 0, mockModel.getPlayerHand(player1).get(0), player1);
    System.out.println("Round 1:\n" + mockModel.getGrid());

    // Round 2: Machine player 2 turn
    controller2.handleMachinePlayerTurn();
    verifyMove(0, 2, mockModel.getPlayerHand(player2).get(0), player2);
    System.out.println("Round 2:\n" + mockModel.getGrid());

    // Round 3: Machine player 1 turn
    controller1.handleMachinePlayerTurn();
    verifyMove(2, 0, mockModel.getPlayerHand(player1).get(0), player1);
    System.out.println("Round 3:\n" + mockModel.getGrid());

    // Round 4: Machine player 2 turn
    controller2.handleMachinePlayerTurn();
    verifyMove(2, 2, mockModel.getPlayerHand(player2).get(0), player2);
    System.out.println("Round 4:\n" + mockModel.getGrid());

    // Round 5: Machine player 1 turn
    controller1.handleMachinePlayerTurn();
    verifyMove(0, 1, mockModel.getPlayerHand(player1).get(0), player1);
    System.out.println("Round 5:\n" + mockModel.getGrid());

    // Round 6: Machine player 2 turn
    controller2.handleMachinePlayerTurn();
    verifyMove(1, 0, mockModel.getPlayerHand(player2).get(0), player2);
    System.out.println("Round 6:\n" + mockModel.getGrid());

    // Round 7: Machine player 1 turn
    controller1.handleMachinePlayerTurn();
    verifyMove(1, 1, mockModel.getPlayerHand(player1).get(0), player1);
    System.out.println("Round 7:\n" + mockModel.getGrid());

    // Round 8: Machine player 2 turn
    controller2.handleMachinePlayerTurn();
    verifyMove(1, 2, mockModel.getPlayerHand(player2).get(0), player2);
    System.out.println("Round 8:\n" + mockModel.getGrid());

    // Round 9: Machine player 1 turn
    controller1.handleMachinePlayerTurn();
    verifyMove(2, 1, mockModel.getPlayerHand(player1).get(0), player1);
    System.out.println("Round 9:\n" + mockModel.getGrid());

    // Check game over
    assertTrue(mockModel.isGameOver());
    assertEquals(player1, mockModel.getWinner());

    assertEquals("Game over. Winner: Red. Score: 9", mockView1.getGameOverMessage());

  }

  private void verifyMove(int expectedRow, int expectedCol, Card expectedCard, IPlayer player) {
    assertTrue(mockModel.isCardPlayed(expectedCard, expectedRow, expectedCol));
    assertFalse(mockModel.getPlayerHand(player).contains(expectedCard));
    assertEquals(expectedCard,
            player == player1 ? mockView1.getLastUpdatedCard() : mockView2.getLastUpdatedCard());
    assertEquals(expectedRow,
            player == player1 ? mockView1.getLastUpdatedRow() : mockView2.getLastUpdatedRow());
    assertEquals(expectedCol,
            player == player1 ? mockView1.getLastUpdatedCol() : mockView2.getLastUpdatedCol());
  }

}
