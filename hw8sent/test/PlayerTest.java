import model.cards.Card;
import players.Player;
import players.PlayerImpl;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * Tests for the Player class to ensure player initialization and card handling.
 */
public class PlayerTest {

  /**
   * Tests player initialization with a specified color and an empty hand.
   */
  @Test
  public void testPlayerInitialization() {
    Player player = new PlayerImpl("RED");
    assertEquals("RED", player.getColor());
    assertTrue(player.getHand().isEmpty());
  }

  /**
   * Tests adding a card to the player's hand.
   */
  @Test
  public void testAddCardToHand() {
    Player player = new PlayerImpl("BLUE");
    Card card = new Card("AngryDragon", 3, 8, 7, 2);
    player.addCard(card);
    assertEquals(1, player.getHand().size());
    assertEquals(card, player.getHand().get(0));
  }

  /**
   * Tests removing a card from the player's hand.
   */
  @Test
  public void testRemoveCardFromHand() {
    Player player = new PlayerImpl("RED");
    Card card = new Card("WorldDragon", 8, 3, 9, 4);
    player.addCard(card);
    player.removeCard(card);
    assertTrue(player.getHand().isEmpty());
  }

  /**
   * Tests the size of the player's hand after adding multiple cards.
   */
  @Test
  public void testPlayerHandSizeAfterMultipleCards() {
    Player player = new PlayerImpl("RED");
    player.addCard(new Card("HeroKnight", 4, 5, 7, 3));
    player.addCard(new Card("SkyWhale", 7, 6, 5, 4));
    assertEquals(2, player.getHand().size());
  }

  /**
   * Tests that the player's hand reflects the correct cards after removing one.
   */
  @Test
  public void testPlayerHandContentsAfterRemoval() {
    Player player = new PlayerImpl("BLUE");
    Card card1 = new Card("HeroKnight", 4, 5, 7, 3);
    Card card2 = new Card("SkyWhale", 7, 6, 5, 4);
    player.addCard(card1);
    player.addCard(card2);
    player.removeCard(card1);
    assertEquals(1, player.getHand().size());
    assertEquals(card2, player.getHand().get(0));
  }
}
