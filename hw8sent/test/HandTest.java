import model.cards.Card;
import model.utils.Hand;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;




import java.util.List;

/**
 * Tests for the Hand class to ensure correct handling of card addition, removal, and storage.
 */
public class HandTest {

  private Hand hand;

  /**
   * Sets up a new Hand instance before each test.
   */
  @Before
  public void setUp() {
    hand = new Hand();
  }

  /**
   * Tests if a new hand is initialized as empty.
   */
  @Test
  public void testHandInitialization() {
    assertTrue(hand.getCards().isEmpty());
  }

  /**
   * Tests adding a single card to the hand.
   */
  @Test
  public void testAddCard() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    hand.addCard(card);
    assertEquals(1, hand.getCards().size());
    assertEquals(card, hand.getCards().get(0));
  }

  /**
   * Tests adding multiple cards to the hand.
   */
  @Test
  public void testAddMultipleCards() {
    hand.addCard(new Card("FireDragon", 8, 3, 6, 2));
    hand.addCard(new Card("HeroKnight", 4, 5, 7, 3));
    assertEquals(2, hand.getCards().size());
  }

  /**
   * Tests removing a card from the hand.
   */
  @Test
  public void testRemoveCard() {
    Card card = new Card("AngryDragon", 3, 8, 7, 2);
    hand.addCard(card);
    hand.removeCard(card);
    assertTrue(hand.getCards().isEmpty());
  }

  /**
   * Tests attempting to remove a card not present in the hand.
   */
  @Test
  public void testRemoveCardNotInHand() {
    hand.addCard(new Card("SkyWhale", 7, 6, 5, 4));
    hand.removeCard(new Card("FireDragon", 8, 3, 6, 2));
    assertEquals(1, hand.getCards().size());
  }

  /**
   * Tests that getCards returns a copy of the hand's card list.
   */
  @Test
  public void testGetCardsReturnsCopy() {
    Card card = new Card("WorldDragon", 8, 3, 9, 4);
    hand.addCard(card);
    List<Card> cardsCopy = hand.getCards();
    cardsCopy.clear();
    assertEquals(1, hand.getCards().size());
  }
}
