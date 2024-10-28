package model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import configuration.ConfigurationReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GameModelInnerTests {

  private GameModel gameModel;
  private Grid grid;
  private List<Card> deck;
  private Player redPlayer;
  private Player bluePlayer;

  @Before
  public void setUp() {
    gameModel = new GameModel();
    // Load grid file
    try {
      String gridConfigPath = "configFiles" + File.separator + "board1WithNoHoles.config";
      grid = ConfigurationReader.readGridConfig(gridConfigPath);
    } catch (IOException e) {
      throw new RuntimeException("Error reading grid configuration.", e);
    }

    // Load deck file
    try {
      String deckConfigPath = "configFiles" + File.separator + "cardsEnoughForAllBoards.config";
      deck = ConfigurationReader.readCardData(deckConfigPath);
    } catch (IOException e) {
      throw new RuntimeException("Error reading deck configuration.", e);
    }
  }

  @Test
  public void startGameWithConfigInitializesGameCorrectly() {
    gameModel.startGameWithConfig(grid, deck, false);
    assertNotNull(gameModel.getRedPlayer());
    assertNotNull(gameModel.getBluePlayer());
    assertEquals(5, gameModel.getPlayerHand(gameModel.getRedPlayer()).size());
    assertEquals(4, gameModel.getPlayerHand(gameModel.getBluePlayer()).size());
  }

  @Test
  public void startGameWithConfigThrowsExceptionWhenNotEnoughCards() {
    List<Card> smallDeck = new ArrayList<>(deck.subList(0, 3));
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      gameModel.startGameWithConfig(grid, smallDeck, false);
    });
    assertEquals("Not enough cards to start game.", exception.getMessage());
  }

  @Test
  public void playCardPlacesCardOnGridAndSwitchesPlayer() {
    gameModel.startGameWithConfig(grid, deck, false);
    Player currentPlayer = gameModel.getCurrentPlayer();
    Card card = currentPlayer.getHand().get(0);
    gameModel.playCard(currentPlayer, card, 0, 0);
    assertEquals(card, ((CardCell) grid.getCell(0, 0)).getCard());
    assertNotEquals(currentPlayer, gameModel.getCurrentPlayer());
  }

  @Test
  public void playCardThrowsExceptionWhenCellNotEmpty() {
    gameModel.startGameWithConfig(grid, deck, false);
    Player currentPlayer = gameModel.getCurrentPlayer();
    Card card = currentPlayer.getHand().get(0);
    gameModel.playCard(currentPlayer, card, 0, 0);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      gameModel.playCard(currentPlayer, card, 0, 0);
    });
    assertEquals("Cell is not empty.", exception.getMessage());
  }

}