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
    ICard card = new Card("MysticEagle", 5, 10, 4, 2);
    assertNotNull(card);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardCreationNullName() {
    ICard card = new Card(null, 5, 10, 4, 2);
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
    ICard card = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals("MysticEagle", card.getName());
  }

  @Test
  public void testGetAttackValuesWorks() {
    ICard card = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals("5", card.getAttackValue(Direction.NORTH));
    assertEquals("A", card.getAttackValue(Direction.SOUTH));
    assertEquals("4", card.getAttackValue(Direction.EAST));
    assertEquals("2", card.getAttackValue(Direction.WEST));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAttackValueInvalidDirection() {
    ICard card = new Card("MysticEagle", 5, 10, 4, 2);
    card.getAttackValue(null);
  }

  @Test
  public void testEqualsSameObject() {
    ICard card = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals(card, card);
  }

  @Test
  public void testEqualsEqualObjects() {
    ICard card1 = new Card("MysticEagle", 5, 10, 4, 2);
    ICard card2 = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals(card1, card2);
  }

  @Test
  public void testEqualsDifferentObjects() {
    ICard card1 = new Card("MysticEagle", 5, 10, 4, 2);
    ICard card2 = new Card("DragonWarrior", 5, 10, 4, 2);
    assertNotEquals(card1, card2);
  }

  @Test
  public void testEqualsNull() {
    ICard card = new Card("MysticEagle", 5, 10, 4, 2);
    assertNotEquals(card, null);
  }

  @Test
  public void testHashCodeEqualObjects() {
    ICard card1 = new Card("MysticEagle", 5, 10, 4, 2);
    ICard card2 = new Card("MysticEagle", 5, 10, 4, 2);
    assertEquals(card1.hashCode(), card2.hashCode());
  }

  @Test
  public void testHashCodeDifferentObjects() {
    ICard card1 = new Card("MysticEagle", 5, 10, 4, 2);
    ICard card2 = new Card("DragonWarrior", 5, 10, 4, 2);
    assertNotEquals(card1.hashCode(), card2.hashCode());
  }
}
