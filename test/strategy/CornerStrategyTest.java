package strategy;

import org.junit.Before;
import org.junit.Test;
import model.Card;
import model.CardValues;
import model.ICard;
import model.MockThreeTriosModel;
import player.IPlayer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for CornerStrategy AI model.
 */
public class CornerStrategyTest {

  private MockThreeTriosModel mockModel;
  private IPlayer redPlayer;
  private IPlayer bluePlayer;
  private Strategy cornerStrategy;

  @Before
  public void setUp() {
    mockModel = new MockThreeTriosModel();
    redPlayer = mockModel.getRedPlayer();
    bluePlayer = mockModel.getBluePlayer();
    cornerStrategy = new CornerStrategy();
    mockModel.startGameWithConfig(mockModel.getGrid());
  }

  @Test
  public void testCornerStrategyProgression() {
    // Round 1: Red player's turn
    // Should play to top-left corner
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = cornerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    ICard cardToPlay = redMove.getCard();
    assertEquals(redPlayer.getHand().get(0), cardToPlay);
    assertEquals(new Card("RedCard1", 1, 1, 1, 1), cardToPlay);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid().getCell(0, 0).getCard());
    System.out.println("Round 1:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 2: Blue player's turn
    // Should play to top-right corner (since a corner needs to be played, but we will choose the
    // uppermost available corner)
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove = cornerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove);
    ICard cardToPlay2 = blueMove.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay2);
    assertEquals(new Card("BlueCard1", 2, 2, 2, 2), cardToPlay2);
    assertTrue(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay2));
    mockModel.playCard(bluePlayer, cardToPlay2, blueMove.getRow(), blueMove.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay2));
    assertEquals(cardToPlay2, mockModel.getGrid().getCell(0, 2).getCard());
    System.out.println("Round 2:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 3: Red player's turn
    // Should play to bottom-left corner (since we only have the bottom 2 corners, we will choose
    // the leftmost corner)
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove2 = cornerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove2);
    ICard cardToPlay3 = redMove2.getCard();
    assertEquals(redPlayer.getHand().get(0), cardToPlay3);
    assertEquals(new Card("RedCard2", 3, 3, 3, 3), cardToPlay3);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    mockModel.playCard(redPlayer, cardToPlay3, redMove2.getRow(), redMove2.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    assertEquals(cardToPlay3, mockModel.getGrid().getCell(2, 0).getCard());
    System.out.println("Round 3:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 4: Blue player's turn
    // Should play to bottom-right corner (since that is the only corner left)
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove2 = cornerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove2);
    ICard cardToPlay4 = blueMove2.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay4);
    assertEquals(new Card("BlueCard2", 5, 5, 5, 5), cardToPlay4);
    mockModel.playCard(bluePlayer, cardToPlay4, blueMove2.getRow(), blueMove2.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay4));
    assertEquals(cardToPlay4, mockModel.getGrid().getCell(2, 2).getCard());
    System.out.println("Round 4:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 5: Red player's turn
    // Should play to uppermost-leftmost available position (0, 1) (since there are no corners left,
    // we will play to next available uppermost-leftmost position using the card index of 0 from
    // hand)
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove3 = cornerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove3);
    ICard cardToPlay5 = redMove3.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay5));
    assertEquals(cardToPlay5, redPlayer.getHand().get(0));
    assertEquals(new Card("RedCard3", 4, 4, 4, 4), cardToPlay5);
    mockModel.playCard(redPlayer, cardToPlay5, redMove3.getRow(), redMove3.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay5));
    assertEquals(cardToPlay5, mockModel.getGrid().getCell(0, 1).getCard());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    System.out.println("Round 5:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 6: Blue player's turn
    // Should play to next available position (1, 0) (since there are no corners left, we will play
    // to next available uppermost-leftmost position using the card index of 0 from hand)
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove3 = cornerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove3);
    ICard cardToPlay6 = blueMove3.getCard();
    assertTrue(bluePlayer.getHand().contains(cardToPlay6));
    assertEquals(cardToPlay6, bluePlayer.getHand().get(0));
    assertEquals(new Card("BlueCard3", 7, 7, 7, 7), cardToPlay6);
    mockModel.playCard(bluePlayer, cardToPlay6, blueMove3.getRow(), blueMove3.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay6));
    assertEquals(cardToPlay6, mockModel.getGrid().getCell(1, 0).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 0).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    System.out.println("Round 6:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 7: Red player's turn
    // Should play to next available position (1, 1) (since there are no corners left, we will play
    // to next available uppermost-leftmost position using the card index of 0 from hand)
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove4 = cornerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove4);
    ICard cardToPlay7 = redMove4.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay7));
    assertEquals(cardToPlay7, redPlayer.getHand().get(0));
    assertEquals(new Card("RedCard4", 6, 6, 6, 6), cardToPlay7);
    mockModel.playCard(redPlayer, cardToPlay7, redMove4.getRow(), redMove4.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay7));
    assertEquals(cardToPlay7, mockModel.getGrid().getCell(1, 1).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 0).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 2).getOwner());
    System.out.println("Round 7:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 8: Blue player's turn
    // Should play to next available position (1, 2) (since there are no corners left, we will play
    // to next available uppermost-leftmost position using the card index of 0 from hand)
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove4 = cornerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove4);
    ICard cardToPlay8 = blueMove4.getCard();
    assertTrue(bluePlayer.getHand().contains(cardToPlay8));
    assertEquals(cardToPlay8, bluePlayer.getHand().get(0));
    assertEquals(new Card("BlueCard4", 9, 9, 9, 9), cardToPlay8);
    mockModel.playCard(bluePlayer, cardToPlay8, blueMove4.getRow(), blueMove4.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay8));
    assertEquals(cardToPlay8, mockModel.getGrid().getCell(1, 2).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 2).getOwner());
    System.out.println("Round 8:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 9: Red player's turn
    // Should play to last available position (2, 1) (since there are no corners left, we will play
    // to next available uppermost-leftmost position using the card index of 0 from hand)
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove5 = cornerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove5);
    ICard cardToPlay9 = redMove5.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay9));
    assertEquals(cardToPlay9, redPlayer.getHand().get(0));
    assertEquals(new Card("RedCard5", CardValues.A.getValue(),
            CardValues.A.getValue(), 1, 1), cardToPlay9);
    mockModel.playCard(redPlayer, cardToPlay9, redMove5.getRow(), redMove5.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay9));
    assertEquals(cardToPlay9, mockModel.getGrid().getCell(2, 1).getCard());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(1, 2).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(2, 2).getOwner());
    System.out.println("Round 9:\n" + mockModel.getGrid());

    // Confirm game state and winner
    assertTrue(mockModel.isGameOver());
    assertEquals(redPlayer, mockModel.getWinner());
  }
}


