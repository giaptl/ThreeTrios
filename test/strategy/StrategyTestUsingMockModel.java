package strategy;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import model.Card;
import model.CardValues;
import model.MockThreeTriosModel;
import model.Player;

import static model.CardValues.A;
import static org.junit.Assert.*;

/**
 * Test file for the Strategies. Using a MockModel to simplify code actions.
 */
public class StrategyTestUsingMockModel {
  private MockThreeTriosModel mockModel;
  private Player redPlayer;
  private Player bluePlayer;
  private Strategy flipMaximizerStrategy;
  private Strategy minimaxStrategy;
  private Strategy leastLikelyFlippedStrategy;
  private Strategy cornerStrategy;
  private Strategy combinedStrategy;

  @Before
  public void setUp() {
    mockModel = new MockThreeTriosModel();
    redPlayer = mockModel.getRedPlayer();
    bluePlayer = mockModel.getBluePlayer();
    flipMaximizerStrategy = new FlipMaximizerStrategy();
    minimaxStrategy = new MinimaxStrategy();
    leastLikelyFlippedStrategy = new LeastLikelyFlippedStrategy();
    cornerStrategy = new CornerStrategy();
    combinedStrategy = new CombinedStrategy(flipMaximizerStrategy);
    mockModel.startGameWithConfig(mockModel.getGrid());
  }

  @Test
  public void testFlipMaximizerStrategyProgression() {
    // Round 1: Red player's turn
    // Since no cards are played yet, should play to top left corner
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    Card cardToPlay = redMove.getCard();
    assertEquals(redPlayer.getHand().get(0), cardToPlay);
    assertEquals(new Card("RedCard1", 1, 1, 1, 1), cardToPlay);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid().getCell(0, 0).getCard());
    System.out.println("Round 1:\n" + mockModel.getGrid());

    // Round 2: Blue player's turn
    assertEquals(bluePlayer.getName(), mockModel.getCurrentPlayer().getName());
    Move blueMove = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove);
    Card cardToPlay2 = blueMove.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay2);
    assertEquals(new Card("BlueCard1", 2, 2, 2, 2), cardToPlay2);
    assertTrue(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay2));
    mockModel.playCard(bluePlayer, cardToPlay2, blueMove.getRow(), blueMove.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(blueMove.getCard()));
    assertEquals(cardToPlay2, mockModel.getGrid().getCell(0, 1).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    System.out.println("Round 2:\n" + mockModel.getGrid());

    // Round 3: Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove2 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove2);
    Card cardToPlay3 = redMove2.getCard();
    assertEquals(redPlayer.getHand().get(0), cardToPlay3);
    assertEquals(new Card("RedCard2", 3, 3, 3, 3), cardToPlay3);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    mockModel.playCard(redPlayer, cardToPlay3, redMove2.getRow(), redMove2.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    assertEquals(cardToPlay3, mockModel.getGrid().getCell(0, 2).getCard());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    System.out.println("Round 3:\n" + mockModel.getGrid());

    // Round 4: Blue player's turn
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove2 = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove2);
    Card cardToPlay4 = blueMove2.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay4);
    assertEquals(new Card("BlueCard2", 5, 5, 5, 5), cardToPlay4);
    mockModel.playCard(bluePlayer, blueMove2.getCard(), blueMove2.getRow(), blueMove2.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay4));
    assertEquals(cardToPlay4, mockModel.getGrid().getCell(1, 2).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    System.out.println("Round 4:\n" + mockModel.getGrid());

    // Round 5: Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove3 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove3);
    Card cardToPlay5 = redMove3.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay5));
    assertEquals(new Card("RedCard4", 6, 6, 6, 6), cardToPlay5);
    mockModel.playCard(redPlayer, cardToPlay5, redMove3.getRow(), redMove3.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay5));
    assertEquals(cardToPlay5, mockModel.getGrid().getCell(1, 1).getCard());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(1, 2).getOwner());
    System.out.println("Round 5:\n" + mockModel.getGrid());

    // Round 6: Blue player's turn
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove3 = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove3);
    Card cardToPlay6 = blueMove3.getCard();
    assertTrue(bluePlayer.getHand().contains(cardToPlay6));
    assertEquals(new Card("BlueCard3", 7, 7, 7, 7), cardToPlay6);
    mockModel.playCard(bluePlayer, cardToPlay6, blueMove3.getRow(), blueMove3.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay6));
    assertEquals(cardToPlay6, mockModel.getGrid().getCell(1, 0).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 2).getOwner());
    System.out.println("Round 6:\n" + mockModel.getGrid());

    // Round 7: Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove4 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove4);
    Card cardToPlay7 = redMove4.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay7));
    assertEquals(new Card("RedCard5", CardValues.A.getValue(), CardValues.A.getValue(), 1, 1), cardToPlay7);
    mockModel.playCard(redPlayer, cardToPlay7, redMove4.getRow(), redMove4.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay7));
    assertEquals(cardToPlay7, mockModel.getGrid().getCell(2, 0).getCard());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(1, 0).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(1, 2).getOwner());
    System.out.println("Round 7:\n" + mockModel.getGrid());

    // Round 8: Blue player's turn
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove4 = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove4);
    Card cardToPlay8 = blueMove4.getCard();
    assertTrue(bluePlayer.getHand().contains(cardToPlay8));
    assertEquals(new Card("BlueCard4", 9, 9, 9, 9), cardToPlay8);
    mockModel.playCard(bluePlayer, cardToPlay8, blueMove4.getRow(), blueMove4.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay8));
    assertEquals(cardToPlay8, mockModel.getGrid().getCell(2, 1).getCard());
    assertEquals(cardToPlay7, mockModel.getGrid().getCell(2, 0).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 2).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 0).getOwner());
    System.out.println("Round 8:\n" + mockModel.getGrid());

    // Round 9: Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove5 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove5);
    Card cardToPlay9 = redMove5.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay9));
    assertEquals(new Card("RedCard3", 4, 4, 4, 4), cardToPlay9);
    mockModel.playCard(redPlayer, cardToPlay9, redMove5.getRow(), redMove5.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay9));
    assertEquals(cardToPlay9, mockModel.getGrid().getCell(2, 2).getCard());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay8));
    assertEquals(cardToPlay8, mockModel.getGrid().getCell(2, 1).getCard());
    assertEquals(cardToPlay7, mockModel.getGrid().getCell(2, 0).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 2).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 1).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(2, 2).getOwner());
    System.out.println("Round 9:\n" + mockModel.getGrid());

    // Confirm game state and winner
    assertTrue(mockModel.isGameOver());
    assertEquals(bluePlayer, mockModel.getWinner());

//  @Test
//  public void testMinimaxStrategyProgression() {
//    for (int i = 0; i < 5; i++) {
//      assertEquals(redPlayer, mockModel.getCurrentPlayer());
//      Move redMove = minimaxStrategy.selectMove(redPlayer, mockModel);
//      assertNotNull(redMove);
//      // Add assertion for minimax strategy behavior
//      mockModel.playCard(redPlayer, redMove.getCard(), redMove.getRow(), redMove.getCol());
//      // assertFalse(mockModel.getPlayerHand(redPlayer).contains(redMove.getCard()));
//      assertEquals(bluePlayer, mockModel.getCurrentPlayer());
//
//      // Simulate blue player's move
//      Card blueCard = mockModel.getPlayerHand(bluePlayer).get(0);
//      mockModel.playCard(bluePlayer, blueCard, 1, 1);
//      assertEquals(redPlayer, mockModel.getCurrentPlayer());
//    }
//  }
//
//  @Test
//  public void testLeastLikelyFlippedStrategyProgression() {
//    for (int i = 0; i < 5; i++) {
//      assertEquals(redPlayer, mockModel.getCurrentPlayer());
//      Move redMove = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
//      assertNotNull(redMove);
//      // Add assertion for least likely flipped strategy behavior
//      mockModel.playCard(redPlayer, redMove.getCard(), redMove.getRow(), redMove.getCol());
//      assertFalse(mockModel.getPlayerHand(redPlayer).contains(redMove.getCard()));
//      assertEquals(bluePlayer, mockModel.getCurrentPlayer());
//
//      // Simulate blue player's move
//      Card blueCard = mockModel.getPlayerHand(bluePlayer).get(0);
//      mockModel.playCard(bluePlayer, blueCard, 1, 1);
//      assertEquals(redPlayer, mockModel.getCurrentPlayer());
//    }
//  }
//
//  @Test
//  public void testCornerStrategyProgression() {
//    for (int i = 0; i < 5; i++) {
//      assertEquals(redPlayer, mockModel.getCurrentPlayer());
//      Move redMove = cornerStrategy.selectMove(redPlayer, mockModel);
//      assertNotNull(redMove);
//      assertTrue(isCornerMove(redMove));
//      mockModel.playCard(redPlayer, redMove.getCard(), redMove.getRow(), redMove.getCol());
//      assertFalse(mockModel.getPlayerHand(redPlayer).contains(redMove.getCard()));
//      assertEquals(bluePlayer, mockModel.getCurrentPlayer());
//
//      // Simulate blue player's move
//      Card blueCard = mockModel.getPlayerHand(bluePlayer).get(0);
//      mockModel.playCard(bluePlayer, blueCard, 1, 1);
//      assertEquals(redPlayer, mockModel.getCurrentPlayer());
//    }
//  }
//
//  @Test
//  public void testCombinedStrategyProgression() {
//    for (int i = 0; i < 5; i++) {
//      assertEquals(redPlayer, mockModel.getCurrentPlayer());
//      Move redMove = combinedStrategy.selectMove(redPlayer, mockModel);
//      assertNotNull(redMove);
//      if (i < 4) {
//        assertTrue(isCornerMove(redMove));
//      } else {
//        assertTrue(isFlipMaximizingMove(redMove));
//      }
//      mockModel.playCard(redPlayer, redMove.getCard(), redMove.getRow(), redMove.getCol());
//      assertFalse(mockModel.getPlayerHand(redPlayer).contains(redMove.getCard()));
//      assertEquals(bluePlayer, mockModel.getCurrentPlayer());
//
//      // Simulate blue player's move
//      Card blueCard = mockModel.getPlayerHand(bluePlayer).get(0);
//      mockModel.playCard(bluePlayer, blueCard, 1, 1);
//      assertEquals(redPlayer, mockModel.getCurrentPlayer());
//    }
//  }
//
//  private boolean isCornerMove(Move move) {
//    int rows = mockModel.getGrid().getRows();
//    int cols = mockModel.getGrid().getColumns();
//    return (move.getRow() == 0 || move.getRow() == rows - 1) &&
//            (move.getCol() == 0 || move.getCol() == cols - 1);
//  }
//
//  private boolean isFlipMaximizingMove(Move move) {
//    int flips = mockModel.getNumCardsAbleToFlip(redPlayer, move.getCard(), move.getRow(), move.getCol());
//    // Assume flip maximizing if it flips at least one card
//    return flips > 0;
//  }
  }}