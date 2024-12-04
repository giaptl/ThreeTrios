import model.grid.Grid;
import model.managers.GameManager;
import model.cards.Card;
import players.Player;
import players.PlayerImpl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class to cover edge cases and confirm the game's rule enforcement.
 */
public class RuleValidationTests {

  private Grid grid;
  private GameManager gameManager;

  @Before
  public void setUp() {
    grid = new Grid(3, 3);
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    gameManager = new GameManager(grid, redPlayer, bluePlayer);
  }

  // Test to confirm that a player cannot play when it's not their turn
  @Test
  public void testPlayerCannotPlayOutOfTurn() {
    Card redCard = new Card("FireDragon", 8, 3, 6, 2);
    Card blueCard = new Card("IceGiant", 4, 5, 7, 3);
    gameManager.playTurn(redCard, 0, 0);
    assertFalse(gameManager.playTurn(redCard, 1, 1));
    assertTrue(gameManager.playTurn(blueCard, 0, 1));
  }

  // Test to confirm that a player cannot play to a location that's out of bounds
  @Test
  public void testPlayerCannotPlayOutOfBounds() {
    Card card = new Card("HeroKnight", 4, 5, 7, 3);
    assertFalse(gameManager.playTurn(card, -1, 0)); // Negative row index
    assertFalse(gameManager.playTurn(card, 3, 3)); // Out-of-bounds index
    assertFalse(gameManager.playTurn(card, 0, 5)); // Column out of bounds
  }

  // Test to confirm that a play correctly flips all the relevant cards in multiple directions
  @Test
  public void testPlayFlipsRelevantCards() {
    Card card1 = new Card("FireDragon", 8, 3, 6, 2);
    Card card2 = new Card("IceGiant", 4, 5, 7, 3);
    Card card3 = new Card("SkyWhale", 3, 8, 2, 7);

    gameManager.playTurn(card1, 0, 0); // Red places card
    gameManager.playTurn(card2, 0, 1); // Blue places card adjacent to Red's card
    gameManager.playTurn(card3, 1, 0); // Red places another card to flip Blue's card
    // in two directions

    // Confirm that Blue's card at (0,1) is flipped to Red's ownership
    assertEquals("RED", grid.getCellOwner(0, 1));
  }

  // Test to confirm that a player can move to a location that flips cards
  @Test
  public void testPlayerCanMoveToLocationThatFlipsCards() {
    Card card1 = new Card("HeroKnight", 5, 4, 6, 3);
    Card card2 = new Card("IceGiant", 4, 7, 3, 5);

    gameManager.playTurn(card1, 1, 1); // Red places card in the center
    gameManager.playTurn(card2, 0, 1); // Blue places card above to flip Red's card below

    // Confirm that the Red card at (1,1) has been flipped to Blue
    assertEquals("BLUE", grid.getCellOwner(1, 1));
  }

  // Test to confirm that a player cannot flip their own cards
  @Test
  public void testPlayerCannotFlipTheirOwnCards() {
    Card card1 = new Card("FireDragon", 8, 3, 6, 2);
    Card card2 = new Card("SkyWhale", 3, 8, 2, 7);

    gameManager.playTurn(card1, 0, 0); // Red places card
    gameManager.playTurn(card2, 1, 0); // Blue places card below it

    // Ensure that Blue cannot place a card to flip a Blue card to Blue
    Card card3 = new Card("SeaSerpent", 4, 5, 6, 7);
    assertFalse(gameManager.playTurn(card3, 2, 0)); // Invalid flip
  }

  // Test to confirm that a game-in-progress can be constructed for testing complex scenarios
  @Test
  public void testGameInProgressConstruction() {
    Card card1 = new Card("FireDragon", 8, 3, 6, 2);
    Card card2 = new Card("HeroKnight", 4, 5, 7, 3);
    Card card3 = new Card("SkyWhale", 3, 8, 2, 7);

    // Construct a game-in-progress by placing cards directly on the grid
    grid.placeCard(0, 0, card1, "RED");
    grid.placeCard(0, 1, card2, "BLUE");
    grid.placeCard(1, 1, card3, "RED");

    assertEquals("RED", grid.getCellOwner(0, 0));
    assertEquals("BLUE", grid.getCellOwner(0, 1));
    assertEquals("RED", grid.getCellOwner(1, 1));
  }
}
