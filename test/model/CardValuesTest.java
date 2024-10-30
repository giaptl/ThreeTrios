package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the enum CardValues class in the model.
 */
public class CardValuesTest {

  @Test
  public void testCardValues() {
    assertEquals(1, CardValues.ONE.getValue());
    assertEquals(2, CardValues.TWO.getValue());
    assertEquals(3, CardValues.THREE.getValue());
    assertEquals(4, CardValues.FOUR.getValue());
    assertEquals(5, CardValues.FIVE.getValue());
    assertEquals(6, CardValues.SIX.getValue());
    assertEquals(7, CardValues.SEVEN.getValue());
    assertEquals(8, CardValues.EIGHT.getValue());
    assertEquals(9, CardValues.NINE.getValue());
    assertEquals(10, CardValues.A.getValue());
  }

}
