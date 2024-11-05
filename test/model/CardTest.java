package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for the Card class in the model.
 */
public class CardTest {

  @Test
  public void testCardCreation() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    assertNotNull(card);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationNullName() {
    Card card = new Card(null, 5, 10, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationNegativeNorthValue() {
    new Card("MysticEagle", -1, 10, 4, 2);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationNegativeSouthValue() {
    new Card("MysticEagle", 1, -1, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationNegativeEastValue() {
    new Card("MysticEagle", 1, 1, -4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationNegativeWestValue() {
    new Card("MysticEagle", 1, 1, 4, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationNorthValueGreaterThan10() {
    new Card("MysticEagle", 11, 1, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationSouthValueGreaterThan10() {
    new Card("MysticEagle", 9, 12, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationEastValueGreaterThan10() {
    new Card("MysticEagle", 1, 1, 14, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationWestValueGreaterThan10() {
    new Card("MysticEagle", 1, 1, 4, 12);
  }

  @Test
  public void testGetName() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals("MysticEagle", card.getName());
  }

  @Test
  public void testGetAttackValuesWorks() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals("5", card.getAttackValue(Direction.NORTH));
    assertEquals("A", card.getAttackValue(Direction.SOUTH));
    assertEquals("4", card.getAttackValue(Direction.EAST));
    assertEquals("2", card.getAttackValue(Direction.WEST));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAttackValueInvalidDirection() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    card.getAttackValue(null);
  }

  @Test
  public void testEqualsSameObject() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals(card, card);
  }

  @Test
  public void testEqualsEqualObjects() {
    Card card1 = new Card("MysticEagle", 5, 10, 4, 2);
    Card card2 = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals(card1, card2);
  }

  @Test
  public void testEqualsDifferentObjects() {
    Card card1 = new Card("MysticEagle", 5, 10, 4, 2);
    Card card2 = new Card("DragonWarrior", 5, 10, 4, 2);
    assertNotEquals(card1, card2);
  }

  @Test
  public void testEqualsNull() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    assertNotEquals(card, null);
  }

  @Test
  public void testHashCodeEqualObjects() {
    Card card1 = new Card("MysticEagle", 5, 10, 4, 2);
    Card card2 = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals(card1.hashCode(), card2.hashCode());
  }

  @Test
  public void testHashCodeDifferentObjects() {
    Card card1 = new Card("MysticEagle", 5, 10, 4, 2);
    Card card2 = new Card("DragonWarrior", 5, 10, 4, 2);
    assertNotEquals(card1.hashCode(), card2.hashCode());
  }
}
