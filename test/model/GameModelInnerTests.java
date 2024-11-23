package model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import configuration.ConfigurationReader;
import player.HumanPlayer;
import player.IPlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertNotNull;

/**
 * Test class which tests the protected/package-private methods in the GameModel class.
 */
public class GameModelInnerTests {

  private GameModel gameModel;
  private Grid grid;
  private List<Card> deck;
  IPlayer player1;
  IPlayer player2;

  @Before
  public void setUp() {
    gameModel = new GameModel();
    // Load grid file
    try {
      String gridConfigPath = "src" + File.separator + "configuration"
              + File.separator + "configFiles" + File.separator + "board1WithNoHoles.config";
      grid = ConfigurationReader.readGridConfig(gridConfigPath);
    } catch (IOException e) {
      throw new RuntimeException("Error reading grid configuration.", e);
    }

    // Load deck file
    try {
      String deckConfigPath = "src" + File.separator + "configuration"
              + File.separator + "configFiles" + File.separator + "cardsEnoughForAllBoards.config";
      deck = ConfigurationReader.readCardData(deckConfigPath);
    } catch (IOException e) {
      throw new RuntimeException("Error reading deck configuration.", e);
    }

    player1 = new HumanPlayer("Abhi", new ArrayList<>());
    player2 = new HumanPlayer("Gia", new ArrayList<>());

    gameModel.startGameWithConfig(grid, deck, false, player1, player2);
  }

  // Tests that players are created properly
  // Tests that both players have the correct number of cards in their hand
  @Test
  public void startGameWithConfigInitializesGameCorrectly() {
    gameModel.startGameWithConfig(grid, deck, false, player1, player2);
    assertNotNull(gameModel.getRedPlayer());
    assertNotNull(gameModel.getBluePlayer());
    assertEquals(5, gameModel.getPlayerHand(gameModel.getRedPlayer()).size());
    assertEquals(4, gameModel.getPlayerHand(gameModel.getBluePlayer()).size());
  }

  // Test that IllegalArgumentException is thrown when the not enough cards to start the game
  @Test
  public void startGameWithConfigThrowsExceptionWhenNotEnoughCards() {
    List<Card> smallDeck = new ArrayList<>(deck.subList(0, 3));
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      gameModel.startGameWithConfig(grid, smallDeck, false, player1, player2);
    });
    assertEquals("Not enough cards to start game.", exception.getMessage());
  }

  // Test that playCard() places the card on the grid and switches the current player
  @Test
  public void playCardPlacesCardOnGridAndSwitchesPlayer() {
    gameModel.startGameWithConfig(grid, deck, false, player1, player2);
    IPlayer currentPlayer = gameModel.getCurrentPlayer();
    Card card = currentPlayer.getHand().get(0);
    gameModel.playCard(currentPlayer, card, 0, 0);
    assertEquals(card, (grid.getCell(0, 0)).getCard());
    assertNotEquals(currentPlayer, gameModel.getCurrentPlayer());
  }

  // Test that IllegalArgumentException is thrown when playCard() is for a cell that is already
  // occupied
  @Test(expected = IllegalArgumentException.class)
  public void playCardThrowsExceptionWhenCellNotEmpty() {
    gameModel.startGameWithConfig(grid, deck, false, player1, player2);
    IPlayer currentPlayer = gameModel.getCurrentPlayer();
    Card card = currentPlayer.getHand().get(0);
    gameModel.playCard(currentPlayer, card, 0, 0);
    // next player plays card to the same place
    IPlayer newPlayer = gameModel.getCurrentPlayer();
    Card card2 = currentPlayer.getHand().get(0);
    gameModel.playCard(newPlayer, card2, 0, 0);
  }

  @Test
  public void testGetNumCardsAbleToFlipNoAdjacentCards() {
    grid.setCell(1, 1, new CardCell(new Card("Card1", 1, 2, 3,
            4), gameModel.getRedPlayer()));
    int numFlips = gameModel.getNumCardsAbleToFlip(gameModel.getRedPlayer(), new Card("Card1", 1, 2,
            3, 4), 1, 1);
    assertEquals(0, numFlips);
  }

  @Test
  public void testGetNumCardsAbleToFlipOneAdjacentCard() {
    Card redsCard = new Card("Card1", 1, 2, 3, 4);
    Card bluesCard = new Card("Card2", 5, 6, 7, 8);
    grid.setCell(0, 0, new CardCell(redsCard, gameModel.getRedPlayer()));
    grid.setCell(0, 1, new CardCell(bluesCard, gameModel.getBluePlayer()));
    Card redsCard2 = new Card("Card1", 1, 2, 3, 8);
    int numFlips = gameModel.getNumCardsAbleToFlip(gameModel.getRedPlayer(), redsCard2, 0, 2);
    assertEquals(1, numFlips);
  }

  @Test
  public void testGetNumCardsAbleToFlipNoFlips() {
    Card redsCard = new Card("Card1", 1, 2, 3, 4);
    Card bluesCard = new Card("Card2", 1, 1, 1, 1);

    grid.setCell(1, 1, new CardCell(redsCard, gameModel.getRedPlayer()));
    grid.setCell(1, 2, new CardCell(bluesCard, gameModel.getBluePlayer()));

    int numFlips = gameModel.getNumCardsAbleToFlip(gameModel.getRedPlayer(),
            new Card("Card1", 1, 2, 3, 4), 2, 2);
    assertEquals(0, numFlips);
  }

  @Test
  public void testGetNumCardsAbleToFlipAllAdjacentCards() {
    Card centerCard = new Card("CenterCard", 9, 9, 9, 9);
    Card topCard = new Card("TopCard", 1, 1, 1, 1);
    Card rightCard = new Card("RightCard", 1, 1, 1, 1);
    Card bottomCard = new Card("BottomCard", 1, 1, 1, 1);
    Card leftCard = new Card("LeftCard", 1, 1, 1, 1);

    grid.setCell(0, 1, new CardCell(topCard, gameModel.getBluePlayer()));
    grid.setCell(1, 2, new CardCell(rightCard, gameModel.getBluePlayer()));
    grid.setCell(2, 1, new CardCell(bottomCard, gameModel.getBluePlayer()));
    grid.setCell(1, 0, new CardCell(leftCard, gameModel.getBluePlayer()));

    int numFlips = gameModel.getNumCardsAbleToFlip(gameModel.getRedPlayer(), centerCard, 1, 1);
    assertEquals(4, numFlips);
  }


  // -------------------------------------------------------------------------------

  @Test
  public void testGetNumCardsAbleToFlipCornerCase() {
    Card cornerCard = new Card("CornerCard", 9, 9, 9, 9);
    Card adjacentCard1 = new Card("Adjacent1", 1, 1, 1, 1);
    Card adjacentCard2 = new Card("Adjacent2", 1, 1, 1, 1);

    grid.setCell(0, 1, new CardCell(adjacentCard1, gameModel.getBluePlayer()));
    grid.setCell(1, 0, new CardCell(adjacentCard2, gameModel.getBluePlayer()));

    int numFlips = gameModel.getNumCardsAbleToFlip(gameModel.getRedPlayer(), cornerCard, 0, 0);
    assertEquals(2, numFlips);
  }

  @Test
  public void testGetNumCardsAbleToFlipConsecutiveCards() {
    Card cornerCard = new Card("Card", 9, 9, 9, 9);
    Card adjacentCard1 = new Card("Adjacent1", 2, 2, 2, 2);
    Card adjacentCard2 = new Card("Adjacent2", 1, 1, 1, 1);

    grid.setCell(0, 1, new CardCell(adjacentCard1, gameModel.getBluePlayer()));
    grid.setCell(0, 2, new CardCell(adjacentCard2, gameModel.getBluePlayer()));

    int numFlips = gameModel.getNumCardsAbleToFlip(gameModel.getRedPlayer(), cornerCard, 0, 0);
    assertEquals(2, numFlips);
  }

  @Test
  public void testGetNumCardsAbleToFlipAllThreeCards() {
    Card centerCard = new Card("1,2", 9, 9, 9, 9);
    Card one = new Card("0,0", 1, 1, 1, 1);
    Card two = new Card("0,1", 2, 2, 2, 2);
    Card three = new Card("0,2", 3, 3, 3, 3);

    grid.setCell(0, 0, new CardCell(one, gameModel.getBluePlayer()));
    grid.setCell(0, 1, new CardCell(two, gameModel.getBluePlayer()));
    grid.setCell(0, 2, new CardCell(three, gameModel.getBluePlayer()));

    int numFlips = gameModel.getNumCardsAbleToFlip(gameModel.getRedPlayer(), centerCard, 1, 2);
    assertEquals(3, numFlips);
  }

  // -------------------------------------------------------------------------------

  @Test
  public void testGetNumCardsAbleToFlipEdgeCase() {
    Card edgeCard = new Card("EdgeCard", 9, 9, 9, 9);
    Card adjacentCard1 = new Card("Adjacent1", 1, 1, 1, 1);
    Card adjacentCard2 = new Card("Adjacent2", 1, 1, 1, 1);
    Card adjacentCard3 = new Card("Adjacent3", 1, 1, 1, 1);

    grid.setCell(0, 0, new CardCell(adjacentCard1, gameModel.getBluePlayer()));
    grid.setCell(0, 2, new CardCell(adjacentCard2, gameModel.getBluePlayer()));
    grid.setCell(1, 1, new CardCell(adjacentCard3, gameModel.getBluePlayer()));

    int numFlips = gameModel.getNumCardsAbleToFlip(gameModel.getRedPlayer(), edgeCard, 0, 1);
    assertEquals(3, numFlips);
  }


  @Test
  public void testStartBattlePhase() {
    // Set up the initial game state
    gameModel.startGameWithConfig(grid, deck, false, player1, player2);

    // Place initial cards on the grid
    Card redCard = new Card("RedCard", 5, 5, 5, 5);
    Card blueCard1 = new Card("BlueCard1", 2, 2, 2, 2);
    Card blueCard2 = new Card("BlueCard2", 1, 1, 1, 1);
    Card blueCard3 = new Card("BlueCard2", 2, 2, 2, 2);
    Card blueCard4 = new Card("BlueCard2", 1, 1, 1, 1);

    grid.setCell(0, 0, new CardCell(redCard, gameModel.getRedPlayer()));
    grid.setCell(0, 1, new CardCell(blueCard1, gameModel.getBluePlayer()));
    grid.setCell(0, 2, new CardCell(blueCard2, gameModel.getBluePlayer()));
    grid.setCell(1, 0, new CardCell(blueCard3, gameModel.getBluePlayer()));
    grid.setCell(2, 0, new CardCell(blueCard4, gameModel.getBluePlayer()));

    // Start the battle phase from the red card
    gameModel.startBattlePhase(0, 0);

    // Check that the blue cards have been flipped to red
    assertEquals(gameModel.getRedPlayer(), (grid.getCell(0, 1)).getOwner());
    assertEquals(gameModel.getRedPlayer(), (grid.getCell(0, 2)).getOwner());
    assertEquals(gameModel.getRedPlayer(), (grid.getCell(1, 0)).getOwner());
    assertEquals(gameModel.getRedPlayer(), (grid.getCell(2, 0)).getOwner());
  }

}