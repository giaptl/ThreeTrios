package strategy;

import org.junit.Before;
import org.junit.Test;
import model.Card;
import model.CardValues;
import model.MockThreeTriosModel;
import model.CardCell;
import model.Player;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the LeastLikelyFlippedStrategy class.
 */
public class LeastLikelyFlippedStrategyTest {

  private MockThreeTriosModel mockModel;
  private Player redPlayer;
  private Player bluePlayer;
  private Strategy leastLikelyFlippedStrategy;

  @Before
  public void setUp() {
    mockModel = new MockThreeTriosModel();
    redPlayer = mockModel.getRedPlayer();
    bluePlayer = mockModel.getBluePlayer();
    leastLikelyFlippedStrategy = new LeastLikelyFlippedStrategy();
    mockModel.startGameWithConfig(mockModel.getGrid());
  }

  @Test
  public void testLeastLikelyFlippedStrategyProgression() {
    // Round 1: Red player's turn
    // Expected: The strategy should choose a corner, preferably (0,0), as it's least likely to be
    // flipped.
    // The weakest card (1,1,1,1) should be played to minimize potential loss.
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    Card cardToPlay = redMove.getCard();
    assertEquals(redPlayer.getHand().get(0), cardToPlay);
    assertEquals(new Card("RedCard1", 1, 1, 1, 1), cardToPlay);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid().getCell(0, 0).getCard());
    System.out.println("Round 1:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 2: Blue player's turn
    // Expected: The strategy should choose another corner, likely (2,2), to minimize flip risk.
    // The card (2,2,2,2) should be played as it's the weakest in Blue's hand.
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove = leastLikelyFlippedStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove);
    Card cardToPlay2 = blueMove.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay2);
    assertEquals(new Card("BlueCard1", 2, 2, 2, 2), cardToPlay2);
    assertTrue(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay2));
    mockModel.playCard(bluePlayer, cardToPlay2, blueMove.getRow(), blueMove.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay2));
    assertEquals(cardToPlay2, mockModel.getGrid().getCell(0, 1).getCard());
    System.out.println("Round 2:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 3: Red player's turn
    // Expected: The strategy should choose another corner, likely (0,2) or (2,0).
    // The card (3,3,3,3) should be played as it's the next weakest in Red's hand.
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove2 = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove2);
    Card cardToPlay3 = redMove2.getCard();
    assertEquals(redPlayer.getHand().get(0), cardToPlay3);
    assertEquals(new Card("RedCard2", 3, 3, 3, 3), cardToPlay3);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    mockModel.playCard(redPlayer, cardToPlay3, redMove2.getRow(), redMove2.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    assertEquals(cardToPlay3, mockModel.getGrid().getCell(0, 2).getCard());
    System.out.println("Round 3:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 4: Blue player's turn
    // Expected: The strategy should choose the last corner, (0,2).
    // The card (5,5,5,5) should be played as it's the next weakest in Blue's hand.
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove2 = leastLikelyFlippedStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove2);
    Card cardToPlay4 = blueMove2.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay4);
    assertEquals(new Card("BlueCard2", 5, 5, 5, 5), cardToPlay4);
    mockModel.playCard(bluePlayer, cardToPlay4, blueMove2.getRow(), blueMove2.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay4));
    assertEquals(cardToPlay4, mockModel.getGrid().getCell(1, 0).getCard());
    System.out.println("Round 4:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 5: Red player's turn
    // Expected: The strategy should choose a side cell, likely (0,1) or (1,0).
    // The card (4,4,4,4) should be played as it's the next weakest in Red's hand.
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove3 = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove3);
    Card cardToPlay5 = redMove3.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay5));
    assertEquals(new Card("RedCard3", 4, 4, 4, 4), cardToPlay5);
    mockModel.playCard(redPlayer, cardToPlay5, redMove3.getRow(), redMove3.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay5));
    assertEquals(cardToPlay5, mockModel.getGrid().getCell(1, 2).getCard());
    System.out.println("Round 5:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 6: Blue player's turn
    // Expected: The strategy should choose another side cell, likely (1,0) or (1,2).
    // The card (7,7,7,7) should be played as it's the next weakest in Blue's hand.
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove3 = leastLikelyFlippedStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove3);
    Card cardToPlay6 = blueMove3.getCard();
    assertTrue(bluePlayer.getHand().contains(cardToPlay6));
    assertEquals(new Card("BlueCard3", 7, 7, 7, 7), cardToPlay6);
    mockModel.playCard(bluePlayer, cardToPlay6, blueMove3.getRow(), blueMove3.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay6));
    assertEquals(cardToPlay6, mockModel.getGrid().getCell(2, 0).getCard());
    System.out.println("Round 6:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 7: Red player's turn
    // Expected: The strategy should choose another side cell, likely (1,2).
    // The card (6,6,6,6) should be played as it's the next weakest in Red's hand.
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove4 = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove4);
    Card cardToPlay7 = redMove4.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay7));
    assertEquals(new Card("RedCard4", 6, 6, 6, 6), cardToPlay7);
    mockModel.playCard(redPlayer, cardToPlay7, redMove4.getRow(), redMove4.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay7));
    assertEquals(cardToPlay7, mockModel.getGrid().getCell(1, 1).getCard());
    System.out.println("Round 7:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 8: Blue player's turn
    // Expected: The strategy should choose one of the two remaining cells, (1,1) or (2,1).
    // The card (9,9,9,9) should be played as it's the strongest in Blue's hand.
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove4 = leastLikelyFlippedStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove4);
    Card cardToPlay8 = blueMove4.getCard();
    assertTrue(bluePlayer.getHand().contains(cardToPlay8));
    assertEquals(new Card("BlueCard4", 9, 9, 9, 9), cardToPlay8);
    mockModel.playCard(bluePlayer, cardToPlay8, blueMove4.getRow(), blueMove4.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay8));
    assertEquals(cardToPlay8, mockModel.getGrid().getCell(2, 1).getCard());
    System.out.println("Round 8:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 9: Red player's turn
    // Expected: The strategy should choose the last remaining cell, (1,1).
    // The card (A,A,1,1) should be played as it's the last card in Red's hand.
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove5 = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove5);
    Card cardToPlay9 = redMove5.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay9));
    assertEquals(new Card("RedCard5", CardValues.A.getValue(),
            CardValues.A.getValue(), 1, 1), cardToPlay9);
    mockModel.playCard(redPlayer, cardToPlay9, redMove5.getRow(), redMove5.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay9));
    assertEquals(cardToPlay9, mockModel.getGrid().getCell(2, 2).getCard());
    System.out.println("Round 9:\n" + mockModel.getGrid());

    // Confirm game state and winner
    // Expected: The game should be over, and Red should win due to having more powerful cards.
    assertTrue(mockModel.isGameOver());
    assertEquals(redPlayer, mockModel.getWinner());
  }

  @Test
  public void testFullBoard() {
    // Set up a full board
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        mockModel.getGrid().setCell(row, col, new CardCell(
                new Card("Card" + row + col, 1, 1, 1, 1), redPlayer));
      }
    }

    // Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
    assertNull(redMove); // No moves should be possible
    System.out.println("Full Board:\n" + mockModel.getGrid());
    assertTrue(mockModel.isGameOver());
  }

  @Test
  public void testEmptyBoardPlaysToTopCorner() {
    // Set up an empty board
    mockModel.startGameWithConfig(mockModel.getGrid());

    // Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    Card cardToPlay = redMove.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(redPlayer.getHand().contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid().getCell(
            redMove.getRow(), redMove.getCol()).getCard());
    System.out.println("Empty Board:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());
  }

  @Test
  public void testMultipleEqualFlips() {
    // Set up the board with multiple equal flips possible
    mockModel.getGrid().setCell(0, 0, new CardCell(
            new Card("RedCard1", 1, 1, 1, 1), redPlayer));
    mockModel.getGrid().setCell(0, 1, new CardCell(
            new Card("BlueCard1", 2, 2, 2, 2), bluePlayer));
    mockModel.getGrid().setCell(0, 2, new CardCell(
            new Card("RedCard2", 3, 3, 3, 3), redPlayer));
    mockModel.getGrid().setCell(1, 0, new CardCell(
            new Card("BlueCard2", 4, 4, 4, 4), bluePlayer));
    mockModel.getGrid().setCell(1, 1, new CardCell(
            new Card("RedCard3", 5, 5, 5, 5), redPlayer));
    mockModel.getGrid().setCell(1, 2, new CardCell(
            new Card("BlueCard3", 6, 6, 6, 6), bluePlayer));
    mockModel.getGrid().setCell(2, 0, new CardCell(
            new Card("RedCard4", 7, 7, 7, 7), redPlayer));
    mockModel.getGrid().setCell(2, 1, new CardCell(
            new Card("BlueCard4", 8, 8, 8, 8), bluePlayer));
    // Two moves left at (2, 2) and (2, 1) with equal flips

    // Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = leastLikelyFlippedStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    Card cardToPlay = redMove.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(redPlayer.getHand().contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid().getCell(
            redMove.getRow(), redMove.getCol()).getCard());
    System.out.println("Multiple Equal Flips:\n" + mockModel.getGrid());
  }
}
