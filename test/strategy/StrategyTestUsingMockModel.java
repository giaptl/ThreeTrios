package strategy;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import model.Card;
import model.MockThreeTriosModel;
import model.Player;

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
    List<Card> redHand = mockModel.getPlayerHand(redPlayer);
    List<Card> blueHand = mockModel.getPlayerHand(bluePlayer);
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
    assertEquals(new Card("RedCard1", 1, 1, 1,1), cardToPlay);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid().getCell(0, 0).getCard());
    System.out.print("Round 1:\n" + mockModel.getGrid());

    // Round 2: Blue player's turn
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove);
    Card cardToPlay2 = blueMove.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay2);
    assertEquals(new Card("BlueCard1", 2, 2, 2,2), cardToPlay2);
    assertTrue(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay2));
    mockModel.playCard(bluePlayer, cardToPlay2, blueMove.getRow(), blueMove.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(blueMove.getCard()));
    assertEquals(cardToPlay2, mockModel.getGrid().getCell(0, 1).getCard());
    System.out.print("Round 2:\n" + mockModel.getGrid());

    // Round 3: Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove2 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove2);
    Card cardToPlay3 = redMove2.getCard();
    assertEquals(redPlayer.getHand().get(0), cardToPlay3);
    assertEquals(new Card("RedCard2", 3, 3, 3,3), cardToPlay3);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    mockModel.playCard(redPlayer, cardToPlay3, redMove2.getRow(), redMove2.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    assertEquals(cardToPlay3, mockModel.getGrid().getCell(0, 2).getCard());
    System.out.print("Round 3:\n" + mockModel.getGrid());

    // Round 4: Blue player's turn
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove2 = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove2);
    Card cardToPlay4 = blueMove2.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay4);
    assertEquals(new Card("BlueCard2", 5, 5, 5,5), cardToPlay4);
    mockModel.playCard(bluePlayer, blueMove2.getCard(), blueMove2.getRow(), blueMove2.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(blueMove2.getCard()));
    assertEquals(cardToPlay4, mockModel.getGrid().getCell(1, 0).getCard());
    System.out.print("Round 4:\n" + mockModel.getGrid());

    // Round 5: Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove3 = combinedStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    //assertTrue(isCornerMove(redMove) || isFlipMaximizingMove(redMove));
  }

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
}