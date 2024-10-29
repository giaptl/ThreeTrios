package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    assertEquals(5, card.getAttackValue(Direction.NORTH));
    assertEquals(10, card.getAttackValue(Direction.SOUTH));
    assertEquals(4, card.getAttackValue(Direction.EAST));
    assertEquals(2, card.getAttackValue(Direction.WEST));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAttackValueInvalidDirection() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    card.getAttackValue(null);
  }
}
