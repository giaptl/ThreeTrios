import model.cards.Card;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for the Card class to ensure proper handling of card attributes and methods.
 */
public class CardTest {

  /**
   * Tests if a card is initialized with the correct name and attack values.
   */
  @Test
  public void testCardInitialization() {
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    assertEquals("FireDragon", card.getName());
    assertEquals(8, card.getNorth());
    assertEquals(3, card.getSouth());
    assertEquals(6, card.getEast());
    assertEquals(2, card.getWest());
  }

  /**
   * Tests the toString method to verify correct string representation.
   */
  @Test
  public void testCardToString() {
    Card card = new Card("IceGiant", 2, 7, 5, 9);
    String expected = "IceGiant (N: 2, S: 7, E: 5, W: 9)";
    assertEquals(expected, card.toString());
  }

  /**
   * Tests edge values for card attacks to ensure handling of minimum and maximum values.
   */
  @Test
  public void testEdgeAttackValues() {
    Card card = new Card("HeroKnight", 1, 10, 1, 10);
    assertEquals(1, card.getNorth());
    assertEquals(10, card.getSouth());
    assertEquals(1, card.getEast());
    assertEquals(10, card.getWest());
  }

  /**
   * Tests that creating a card with negative values throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeAttackValue() {
    new Card("CorruptKing", -1, 3, 9, 2);
  }

  /**
   * Tests that attack values can differ on each side of the card.
   */
  @Test
  public void testDifferentAttackValues() {
    Card card = new Card("MixedDragon", 9, 7, 8, 6);
    assertNotEquals(card.getNorth(), card.getSouth());
    assertNotEquals(card.getEast(), card.getWest());
  }

  /**
   * Tests handling of long card names to ensure they are stored correctly.
   */
  @Test
  public void testLongCardName() {
    Card card = new Card("SuperUltraMegaEpicDragonThatDefiesTheHeavens", 7, 4, 3, 5);
    assertEquals("SuperUltraMegaEpicDragonThatDefiesTheHeavens", card.getName());
  }

  /**
   * Tests equality of cards with identical attributes.
   */
  @Test
  public void testCardsEquality() {
    Card card1 = new Card("FireDragon", 8, 3, 6, 2);
    Card card2 = new Card("FireDragon", 8, 3, 6, 2);
    assertEquals(card1.toString(), card2.toString());
  }

  /**
   * Tests inequality of cards with different attributes.
   */
  @Test
  public void testCardsInequality() {
    Card card1 = new Card("FireDragon", 8, 3, 6, 2);
    Card card2 = new Card("IceGiant", 2, 7, 5, 9);
    assertNotEquals(card1.toString(), card2.toString());
  }
}
