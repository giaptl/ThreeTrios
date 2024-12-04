package strategy;

import org.junit.Before;
import org.junit.Test;


import model.Card;
import model.CardCell;
import model.CardValues;
import model.ICard;
import model.MockThreeTriosModel;
import player.IPlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Test file for the Strategies. Using a MockModel to simplify code actions.
 */
public class FlipMaximizerStrategyTest {

  private MockThreeTriosModel mockModel;
  private IPlayer redPlayer;
  private IPlayer bluePlayer;
  private Strategy flipMaximizerStrategy;

  @Before
  public void setUp() {
    mockModel = new MockThreeTriosModel();
    redPlayer = mockModel.getRedPlayer();
    bluePlayer = mockModel.getBluePlayer();
    flipMaximizerStrategy = new FlipMaximizerStrategy();
    mockModel.startGameWithConfig(mockModel.getGrid());
  }

  // This test is thoroughly explained in the strategy-transcript.txt file.
  @Test
  public void testFlipMaximizerStrategyProgression() {
    // Round 1: Red player's turn
    // Since no cards are played yet, should play to top left corner
    // TESTS TOP-LEFT CORNER PLACEMENT ON FIRST MOVE
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
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
    // Should place at 0,1 since that maximizes flips and will be placed in the uppermost row
    assertEquals(bluePlayer.getName(), mockModel.getCurrentPlayer().getName());
    Move blueMove = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove);
    ICard cardToPlay2 = blueMove.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay2);
    assertEquals(new Card("BlueCard1", 2, 2, 2, 2), cardToPlay2);
    assertTrue(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay2));
    mockModel.playCard(bluePlayer, cardToPlay2, blueMove.getRow(), blueMove.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(blueMove.getCard()));
    assertEquals(cardToPlay2, mockModel.getGrid().getCell(0, 1).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    System.out.println("Round 2:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 3: Red player's turn
    // Should place at 0,2 since that maximizes flips. It places uppermost before leftmost grid tile
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove2 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove2);
    ICard cardToPlay3 = redMove2.getCard();
    assertEquals(redPlayer.getHand().get(0), cardToPlay3);
    assertEquals(new Card("RedCard2", 3, 3, 3, 3), cardToPlay3);
    assertTrue(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    mockModel.playCard(redPlayer, cardToPlay3, redMove2.getRow(), redMove2.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay3));
    assertEquals(cardToPlay3, mockModel.getGrid().getCell(0, 2).getCard());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(redPlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    System.out.println("Round 3:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    // Round 4: Blue player's turn
    // Should place at 1,2 since it maximizes flips.
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove2 = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove2);
    ICard cardToPlay4 = blueMove2.getCard();
    assertEquals(bluePlayer.getHand().get(0), cardToPlay4);
    assertEquals(new Card("BlueCard2", 5, 5, 5, 5), cardToPlay4);
    mockModel.playCard(bluePlayer, blueMove2.getCard(), blueMove2.getRow(), blueMove2.getCol());
    assertFalse(mockModel.getPlayerHand(bluePlayer).contains(cardToPlay4));
    assertEquals(cardToPlay4, mockModel.getGrid().getCell(1, 2).getCard());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 0).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 1).getOwner());
    assertEquals(bluePlayer, mockModel.getGrid().getCell(0, 2).getOwner());
    System.out.println("Round 4:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());

    /*
    Round 5: Red player's turn
    <p>
    Should place at (1,1) since it maximizes flips and this is closer to the top left corner than
    the other option, which was placing it at (2,2).
    <p>
    This part of the test is testing the tie and making sure that the card is placed at the tile
    that is closer to the top left corner. Proving our tiebreaker is working to spec.
   */
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove3 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove3);
    ICard cardToPlay5 = redMove3.getCard();
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
    assertFalse(mockModel.isGameOver());

    /*
     Round 6: Blue player's turn
     Maximizes flips by placing at 1,0. Which will flip the most cards.
    */
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove3 = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove3);
    ICard cardToPlay6 = blueMove3.getCard();
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
    assertFalse(mockModel.isGameOver());

    /*
     Round 7: Red player's turn
     Should place at 2,0 since it maximizes flips using the strongest card (A = 10).
    */
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove4 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove4);
    ICard cardToPlay7 = redMove4.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay7));
    assertEquals(new Card("RedCard5", CardValues.A.getValue(),
            CardValues.A.getValue(), 1, 1), cardToPlay7);
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
    assertFalse(mockModel.isGameOver());

    /*
     Round 8: Blue player's turn
     Should place at 2,1 since it maximizes flips by placing next to multiple red cards.
    */
    assertEquals(bluePlayer, mockModel.getCurrentPlayer());
    Move blueMove4 = flipMaximizerStrategy.selectMove(bluePlayer, mockModel);
    assertNotNull(blueMove4);
    ICard cardToPlay8 = blueMove4.getCard();
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
    assertFalse(mockModel.isGameOver());

    /*
     Round 9: Red player's turn
     Should place at 2,2 since it is the last available space on the grid.
    */
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove5 = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove5);
    ICard cardToPlay9 = redMove5.getCard();
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
  }

  @Test
  public void testSingleMoveLeft() {
    // Set up the board with only one move left
    mockModel.getGrid().setCell(0, 0, new CardCell(new Card(
            "RedCard1", 1, 1, 1, 1), redPlayer));
    mockModel.getGrid().setCell(0, 1, new CardCell(new Card(
            "BlueCard1", 2, 2, 2, 2), bluePlayer));
    mockModel.getGrid().setCell(0, 2, new CardCell(new Card(
            "RedCard2", 3, 3, 3, 3), redPlayer));
    mockModel.getGrid().setCell(1, 0, new CardCell(new Card(
            "BlueCard2", 5, 5, 5, 5), bluePlayer));
    mockModel.getGrid().setCell(1, 1, new CardCell(new Card(
            "RedCard3", 4, 4, 4, 4), redPlayer));
    mockModel.getGrid().setCell(1, 2, new CardCell(new Card(
            "BlueCard3", 7, 7, 7, 7), bluePlayer));
    mockModel.getGrid().setCell(2, 0, new CardCell(new Card(
            "RedCard4", 6, 6, 6, 6), redPlayer));
    mockModel.getGrid().setCell(2, 1, new CardCell(new Card(
            "BlueCard4", 9, 9, 9, 9), bluePlayer));
    // Only one move left at (2, 2)

    // Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    ICard cardToPlay = redMove.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid().getCell(2, 2).getCard());
    System.out.println("Single Move Left:\n" + mockModel.getGrid());
    assertTrue(mockModel.isGameOver());
  }

  @Test
  public void testEmptyBoard() {
    // Set up an empty board
    mockModel.startGameWithConfig(mockModel.getGrid());

    // Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    ICard cardToPlay = redMove.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid()
            .getCell(redMove.getRow(), redMove.getCol()).getCard());
    System.out.println("Empty Board:\n" + mockModel.getGrid());
    assertFalse(mockModel.isGameOver());
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
    Move redMove = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNull(redMove); // No moves should be possible
    System.out.println("Full Board:\n" + mockModel.getGrid());
    assertTrue(mockModel.isGameOver());
  }

  @Test
  public void testMultipleEqualFlips() {
    // Set up the board with multiple equal flips possible
    mockModel.getGrid().setCell(0, 0, new CardCell(new Card(
            "RedCard1", 1, 1, 1, 1), redPlayer));
    mockModel.getGrid().setCell(0, 1, new CardCell(new Card(
            "BlueCard1", 2, 2, 2, 2), bluePlayer));
    mockModel.getGrid().setCell(0, 2, new CardCell(new Card(
            "RedCard2", 3, 3, 3, 3), redPlayer));
    mockModel.getGrid().setCell(1, 0, new CardCell(new Card(
            "BlueCard2", 4, 4, 4, 4), bluePlayer));
    mockModel.getGrid().setCell(1, 1, new CardCell(new Card(
            "RedCard3", 5, 5, 5, 5), redPlayer));
    mockModel.getGrid().setCell(1, 2, new CardCell(new Card(
            "BlueCard3", 6, 6, 6, 6), bluePlayer));
    mockModel.getGrid().setCell(2, 0, new CardCell(new Card(
            "RedCard4", 7, 7, 7, 7), redPlayer));
    mockModel.getGrid().setCell(2, 1, new CardCell(new Card(
            "BlueCard4", 8, 8, 8, 8), bluePlayer));
    // Two moves left at (2, 2) and (2, 1) with equal flips

    // Red player's turn
    assertEquals(redPlayer, mockModel.getCurrentPlayer());
    Move redMove = flipMaximizerStrategy.selectMove(redPlayer, mockModel);
    assertNotNull(redMove);
    ICard cardToPlay = redMove.getCard();
    assertTrue(redPlayer.getHand().contains(cardToPlay));
    mockModel.playCard(redPlayer, cardToPlay, redMove.getRow(), redMove.getCol());
    assertFalse(mockModel.getPlayerHand(redPlayer).contains(cardToPlay));
    assertEquals(cardToPlay, mockModel.getGrid().getCell(
            redMove.getRow(), redMove.getCol()).getCard());
    System.out.println("Multiple Equal Flips:\n" + mockModel.getGrid());
  }

}