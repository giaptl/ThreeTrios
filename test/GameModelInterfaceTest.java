//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import configuration.ConfigurationReader;
//import model.Card;
//import model.Direction;
//import model.GameModel;
//import model.Grid;
//import player.IPlayer;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//
///**
// * This class tests the methods of the interface IGameModel which
// * are implemented currently within the GameModel class.
// */
//public class GameModelInterfaceTest {
//
//  private GameModel gameModel;
//  private Grid grid;
//  private List<Card> deck;
//
//  /**
//   * Set up the game model and load the grid and deck files.
//   */
//  @BeforeEach
//  public void setUp() {
//    gameModel = new GameModel();
//    // Load grid file
//    try {
//      String gridConfigPath = "src" + File.separator + "configuration"
//              + File.separator + "configFiles" + File.separator + "board1WithNoHoles.config";
//      grid = ConfigurationReader.readGridConfig(gridConfigPath);
//    } catch (IOException e) {
//      throw new RuntimeException("Error reading grid configuration.", e);
//    }
//
//    // Load deck file
//    try {
//      String deckConfigPath = "src" + File.separator + "configuration"
//              + File.separator + "configFiles" + File.separator + "cardsEnoughForAllBoards.config";
//      deck = ConfigurationReader.readCardData(deckConfigPath);
//    } catch (IOException e) {
//      throw new RuntimeException("Error reading deck configuration.", e);
//    }
//  }
//
//
//  /**
//   * Tests the `startGameWithConfig` method to ensure that the game initializes correctly
//   * with edge cases for the number of cards. Verifies that both players' hands are correctly
//   * populated with the expected number of cards when the minimum and maximum number of cards
//   * are provided.
//   */
//  @Test
//  public void testStartGameWithConfigEdgeCases() {
//    // Test with minimum number of cards
//    int minCards = grid.getNumCardCells() + 1;
//    List<Card> minDeck = new ArrayList<>(deck.subList(0, minCards));
//    gameModel.startGameWithConfig(grid, minDeck, false);
//    assertEquals(minCards / 2, gameModel.getPlayerHand(gameModel.getRedPlayer()).size());
//    assertEquals(minCards / 2 - 1, gameModel.getPlayerHand(
//            gameModel.getBluePlayer()).size());
//
//    // Test with maximum number of cards (assuming a reasonable upper limit)
//    int maxCards = 10;
//    List<Card> maxDeck = new ArrayList<>(deck);
//    while (maxDeck.size() < maxCards) {
//      maxDeck.addAll(deck);
//    }
//    maxDeck = maxDeck.subList(0, maxCards);
//    gameModel.startGameWithConfig(grid, maxDeck, false);
//    assertEquals(maxCards / 2, gameModel.getPlayerHand(gameModel.getRedPlayer()).size());
//    assertEquals(maxCards / 2 - 1, gameModel.getPlayerHand(
//            gameModel.getBluePlayer()).size());
//  }
//
//  /**
//   * Tests the `startGameWithConfig` method to ensure that it throws an IllegalArgumentException
//   * when there are not enough cards to start the game. Verifies that the exception message
//   * matches the expected message.
//   */
//  @Test
//  public void testStartGameWithConfigThrowsExceptionWhenNotEnoughCards() {
//    // Create a small deck with only 3 cards
//    List<Card> smallDeck = new ArrayList<>(deck.subList(0, 3));
//
//    // Verify that starting the game with insufficient cards throws an IllegalArgumentException
//    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//            gameModel.startGameWithConfig(grid, smallDeck, false));
//
//    // Check that the exception message is as expected
//    assertEquals("Not enough cards to start game.", exception.getMessage());
//  }
//
//  /**
//   * Tests the `playCard` method to ensure that a card is correctly played on the grid.
//   * Verifies that the card is placed in the specified cell and that the current player changes.
//   */
//  @Test
//  public void testPlayCard() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer currentPlayer = gameModel.getCurrentPlayer();
//    Card card = currentPlayer.getHand().get(0);
//    gameModel.playCard(currentPlayer, card, 0, 0);
//    assertEquals(card, (grid.getCell(0, 0)).getCard());
//    assertNotEquals(currentPlayer, gameModel.getCurrentPlayer());
//  }
//
//  /**
//   * Tests the `playCard` method to ensure that it throws an IllegalArgumentException
//   * when attempting to play a card in a cell that is already occupied. Verifies that
//   * the exception message matches the expected message.
//   */
//  @Test
//  public void testPlayCardThrowsExceptionWhenCellNotEmpty() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer currentPlayer = gameModel.getCurrentPlayer();
//    Card card = currentPlayer.getHand().get(0);
//    gameModel.playCard(currentPlayer, card, 0, 0);
//    IPlayer newPlayer = gameModel.getCurrentPlayer();
//    Card card2 = newPlayer.getHand().get(0);
//    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//      gameModel.playCard(newPlayer, card2, 0, 0);
//    });
//    assertEquals("Cell is not empty.", exception.getMessage());
//  }
//
//  /**
//   * Tests the `startBattlePhase` method to ensure that the battle phase is initiated after a card
//   * is played. Verifies that the card's owner is correctly updated based on the battle outcome.
//   */
//  @Test
//  public void testStartBattlePhase() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer currentPlayer = gameModel.getCurrentPlayer();
//    Card card = currentPlayer.getHand().get(0);
//    gameModel.playCard(currentPlayer, card, 0, 0);
//    gameModel.startBattlePhase(0, 0);
//
//    assertEquals(grid.getCell(0, 0).getOwner(), currentPlayer);
//  }
//
//  /**
//   * Tests the `isGameOver` method to ensure that it correctly identifies when the game is over.
//   * Verifies that the game is not over at the start and identifies the game over condition.
//   */
//  @Test
//  public void testIsGameOverAndWhenGameIsOver() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    assertFalse(gameModel.isGameOver());
//
//    // Simulate game over condition by filling all cells
//    for (int row = 0; row < grid.getRows(); row++) {
//      for (int col = 0; col < grid.getColumns(); col++) {
//        if (grid.getCell(row, col).isEmpty()) {
//          IPlayer currentPlayer = gameModel.getCurrentPlayer();
//          Card card = currentPlayer.getHand().get(0);
//          gameModel.playCard(currentPlayer, card, row, col);
//        }
//      }
//    }
//    // Verify that the game is over
//    assertTrue(gameModel.isGameOver());
//    // Verify that the winner is correctly determined
//    assertNotNull(gameModel.getWinner());
//  }
//
//  /**
//   * Tests the `playCard` method to ensure that a card can be played in all grid positions.
//   * Verifies that the card is placed in the specified cell, the current player changes,
//   * and the game is correctly reset after each move.
//   */
//  @Test
//  public void testPlayCardInAllGridPositions() {
//    gameModel.startGameWithConfig(grid, deck, false);
//
//    for (int row = 0; row < grid.getRows(); row++) {
//      for (int col = 0; col < grid.getColumns(); col++) {
//        if (grid.getCell(row, col).isEmpty()) {
//          IPlayer currentPlayer = gameModel.getCurrentPlayer();
//          Card card = currentPlayer.getHand().get(0);
//          gameModel.playCard(currentPlayer, card, row, col);
//
//          assertEquals(card, grid.getCell(row, col).getCard());
//          assertNotEquals(currentPlayer, gameModel.getCurrentPlayer());
//          gameModel.startGameWithConfig(grid, deck, false);
//        }
//      }
//    }
//    assertTrue(gameModel.isGameOver());
//  }
//
//  /**
//   * Tests the `playCard` method to ensure that it handles the scenario where a player
//   * attempts to play a card when their hand is empty. Verifies that the game correctly
//   * transitions through all cells and throws an IllegalArgumentException when trying
//   * to play a card after the game is over.
//   */
//  @Test
//  public void testPlayCardWithEmptyHand() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer currentPlayer = gameModel.getCurrentPlayer();
//    int row = 0;
//    int col = 0;
//
//    while (!currentPlayer.getHand().isEmpty()) {
//      Card card = currentPlayer.getHand().get(0);
//      gameModel.playCard(currentPlayer, card, row, col);
//      currentPlayer = gameModel.getCurrentPlayer();
//      // Move to the next cell
//      col++;
//      if (col >= grid.getColumns()) {
//        col = 0;
//        row++;
//        // Check if row exceeds grid's row count
//        if (row >= grid.getRows()) {
//          break;
//        }
//      }
//    }
//    IPlayer finalCurrentPlayer = currentPlayer;
//
//    // Should throw since the game is over
//    assertThrows(IllegalArgumentException.class, () ->
//            gameModel.playCard(finalCurrentPlayer, null, 0, 1));
//  }
//
//  /**
//   * Tests the `playCard` method to ensure that cards are played until the game is over.
//   * Verifies that the game correctly transitions through all cells,
//   * the game ends when all cells are filled and the winner is correctly determined.
//   */
//  @Test
//  public void testConsecutivePlayCardUntilGameOver() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    int playCount = 0;
//
//    while (!gameModel.isGameOver()) {
//      IPlayer currentPlayer = gameModel.getCurrentPlayer();
//      if (currentPlayer.getHand().isEmpty()) {
//        break;
//      }
//
//      Card card = currentPlayer.getHand().get(0);
//      int[] emptyCell = findFirstEmptyCell(grid);
//      if (emptyCell == null) {
//        break;
//      }
//      gameModel.playCard(currentPlayer, card, emptyCell[0], emptyCell[1]);
//      playCount++;
//    }
//    assertTrue(gameModel.isGameOver());
//    assertNotNull(gameModel.getWinner());
//    assertEquals(grid.getNumCardCells(), playCount);
//  }
//
//  // Helper method for test above to find first empty cell
//  private int[] findFirstEmptyCell(Grid grid) {
//    for (int row = 0; row < grid.getRows(); row++) {
//      for (int col = 0; col < grid.getColumns(); col++) {
//        if (grid.getCell(row, col).isEmpty()) {
//          return new int[]{row, col};
//        }
//      }
//    }
//    return null;
//  }
//
//  /**
//   * Tests the `playCard` method to ensure that it throws an IllegalArgumentException
//   * when attempting to play a card out of the grid bounds.
//   */
//  @Test
//  public void testPlayCardOutOfBounds() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer currentPlayer = gameModel.getCurrentPlayer();
//    Card card = currentPlayer.getHand().get(0);
//    assertThrows(IllegalArgumentException.class, () ->
//            gameModel.playCard(currentPlayer, card, -1, 0));
//    assertThrows(IllegalArgumentException.class, () ->
//            gameModel.playCard(currentPlayer, card, 0, -1));
//    assertThrows(IllegalArgumentException.class, () ->
//            gameModel.playCard(currentPlayer, card, grid.getRows(), 0));
//    assertThrows(IllegalArgumentException.class, () ->
//            gameModel.playCard(currentPlayer, card, 0, grid.getColumns()));
//  }
//
//  /**
//   * Ensures that the `playCard` method throws an `IllegalStateException` when attempting
//   * to play a card after the game is over.
//   */
//  @Test
//  public void testPlayCardWhenGameIsOverThrowsException() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    // Fill all cells to end the game
//    for (int i = 0; i < grid.getRows(); i++) {
//      for (int j = 0; j < grid.getColumns(); j++) {
//        if (grid.getCell(i, j).isEmpty()) {
//          IPlayer currentPlayer = gameModel.getCurrentPlayer();
//          Card card = currentPlayer.getHand().get(0);
//          gameModel.playCard(currentPlayer, card, i, j);
//        }
//      }
//    }
//    assertTrue(gameModel.isGameOver());
//    IPlayer currentPlayer = gameModel.getCurrentPlayer();
//    if (!currentPlayer.getHand().isEmpty()) {
//      Card card = currentPlayer.getHand().get(0);
//      assertThrows(IllegalArgumentException.class, () -> {
//        gameModel.playCard(currentPlayer, card, 0, 0);
//      });
//    }
//  }
//
//  /**
//   * The testPlayCardWithWrongPlayer method ensures that the playCard method throws an
//   * IllegalArgumentException when a player tries to play a card that belongs to the wrong player.
//   */
//  @Test
//  public void testPlayCardWithWrongPlayer() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer currentPlayer = gameModel.getCurrentPlayer();
//    IPlayer wrongPlayer;
//    if (currentPlayer == gameModel.getRedPlayer()) {
//      wrongPlayer = gameModel.getBluePlayer();
//    } else {
//      wrongPlayer = gameModel.getRedPlayer();
//    }
//    Card card = wrongPlayer.getHand().get(0);
//    assertThrows(IllegalArgumentException.class, ()
//        -> gameModel.playCard(wrongPlayer, card, 0, 0));
//  }
//
//  /**
//   * Test player CANNOT flip their own cards.
//   */
//  @Test
//  public void testPlayerCannotFlipOwnCards() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer redPlayer = gameModel.getRedPlayer();
//    IPlayer bluePlayer = gameModel.getBluePlayer();
//
//    // Red player plays a card
//    Card redCard1 = redPlayer.getHand().get(0);
//    gameModel.playCard(redPlayer, redCard1, 0, 0);
//
//    Card blueCard1 = bluePlayer.getHand().get(0);
//    gameModel.playCard(bluePlayer, blueCard1, 2, 2);
//
//    // Red player plays another card adjacent to the first card
//    Card redCard2 = redPlayer.getHand().get(1);
//    gameModel.playCard(redPlayer, redCard2, 0, 1);
//
//    // Start battle phase
//    gameModel.startBattlePhase(0, 1);
//
//    // Ensure that the red player did not flip their own card
//    assertEquals(redPlayer, grid.getCell(0, 0).getOwner());
//    assertEquals(redPlayer, grid.getCell(0, 1).getOwner());
//  }
//
//  // GetGrid tests
//
//  /**
//   * Tests the getGrid method to ensure that the grid is correctly initialized
//   * after starting the game.
//   */
//  @Test
//  public void testGetGridAfterStartGame() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    Grid retrievedGrid = gameModel.getGrid();
//    assertNotNull(retrievedGrid);
//    assertEquals(grid.toString(), retrievedGrid.toString());
//  }
//
//  /**
//   * Tests the getGrid method to ensure that the grid remains unchanged
//   * after retrieving it.
//   */
//  @Test
//  public void testGetGridUnchangedAfterRetrieval() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    Grid initialGrid = gameModel.getGrid();
//    // Perform some operations that should not change the grid
//    IPlayer currentPlayer = gameModel.getCurrentPlayer();
//    Grid retrievedGrid = gameModel.getGrid();
//    assertEquals(initialGrid.toString(), retrievedGrid.toString());
//  }
//
//  // Testing of getPlayerScore
//  @Test
//  public void testGetPlayerScoreInitial() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer redPlayer = gameModel.getRedPlayer();
//    IPlayer bluePlayer = gameModel.getBluePlayer();
//
//    assertEquals(0, gameModel.getPlayerScore(redPlayer));
//    assertEquals(0, gameModel.getPlayerScore(bluePlayer));
//  }
//
//  @Test
//  public void testGetPlayerScoreAfterPlayingCards() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer redPlayer = gameModel.getRedPlayer();
//    IPlayer bluePlayer = gameModel.getBluePlayer();
//
//    // Red player plays a card
//    Card redCard = redPlayer.getHand().get(0);
//    gameModel.playCard(redPlayer, redCard, 0, 0);
//
//    // Blue player plays a card
//    Card blueCard = bluePlayer.getHand().get(0);
//    gameModel.playCard(bluePlayer, blueCard, 1, 1);
//
//    assertEquals(1, gameModel.getPlayerScore(redPlayer));
//    assertEquals(1, gameModel.getPlayerScore(bluePlayer));
//  }
//
//  @Test
//  public void testGetPlayerScoreAfterCardFlipBattle() {
//    gameModel.startGameWithConfig(grid, deck, false);
//    IPlayer redPlayer = gameModel.getRedPlayer();
//    IPlayer bluePlayer = gameModel.getBluePlayer();
//
//    // Red player plays a card
//    Card redCard = redPlayer.getHand().get(3);
//    System.out.println(redCard.getAttackValue(Direction.EAST));
//    gameModel.playCard(redPlayer, redCard, 0, 0);
//
//    // Blue player plays a card
//    Card blueCard = bluePlayer.getHand().get(0);
//    System.out.println(blueCard.getAttackValue(Direction.WEST));
//    gameModel.playCard(bluePlayer, blueCard, 0, 1);
//
//    // Start battle phase
//    gameModel.startBattlePhase(0, 1);
//
//    // Assuming red player wins the battle
//    assertEquals(0, gameModel.getPlayerScore(redPlayer));
//    assertEquals(2, gameModel.getPlayerScore(bluePlayer));
//  }
//}