package model;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the CardCell class.
 */
public class CardCellTest {
  @Test
  public void testCardCellCreation() {
    CardCell cell = new CardCell();
    assertNotNull(cell);
    assertTrue(cell.isEmpty());
    assertFalse(cell.isOccupied());
    assertFalse(cell.isHole());
  }

  @Test
  public void testCardCellWithCardAndOwner() {
    Card card = new Card("TestCard", 4, 2, 3, 1);
    IPlayer player = new HumanPlayer("TestPlayer", List.of(card));
    CardCell cell = new CardCell(card, player);

    assertNotNull(cell);
    assertFalse(cell.isEmpty());
    assertTrue(cell.isOccupied());
    assertEquals(card, cell.getCard());
    assertEquals(player, cell.getOwner());
  }

  @Test
  public void testSetOwner() {
    Card card = new Card("TestCard", 1, 2, 3, 4);
    IPlayer player1 = new HumanPlayer("Player1", List.of(card));
    IPlayer player2 = new HumanPlayer("Player2", List.of(card));
    CardCell cell = new CardCell(card, player1);

    assertEquals(player1, cell.getOwner());
    cell.setOwner(player2);
    assertEquals(player2, cell.getOwner());
  }


  @Test
  public void testEqualsEqualObjects() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    IPlayer player = new HumanPlayer("Player1", List.of(card));
    CardCell cardCell1 = new CardCell(card, player);
    CardCell cardCell2 = new CardCell(card, player);
    assertEquals(cardCell1, cardCell2);
  }

  @Test
  public void testEqualsDifferentObjects() {
    Card card1 = new Card("MysticEagle", 5, 10, 4, 2);
    Card card2 = new Card("DragonWarrior", 5, 10, 4, 2);
    IPlayer player1 = new HumanPlayer("Player1", List.of(card1));
    IPlayer player2 = new HumanPlayer("Player2", List.of(card2));
    CardCell cardCell1 = new CardCell(card1, player1);
    CardCell cardCell2 = new CardCell(card2, player2);
    assertNotEquals(cardCell1, cardCell2);
  }

  @Test
  public void testEqualsNull() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    IPlayer player = new HumanPlayer("Player1", List.of(card));
    CardCell cardCell = new CardCell(card, player);
    assertNotEquals(null, cardCell);
  }

  @Test
  public void testEqualsDifferentClass() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    IPlayer player = new HumanPlayer("Player1", List.of(card));
    Cell cardCell = new CardCell(card, player);
    Cell notACardCell = new Hole();
    assertNotEquals(cardCell, notACardCell);
  }

  @Test
  public void testHashCodeEqualObjects() {
    Card card = new Card("MysticEagle", 5, 10, 4, 2);
    IPlayer player = new HumanPlayer("Player1", List.of(card));
    CardCell cardCell1 = new CardCell(card, player);
    CardCell cardCell2 = new CardCell(card, player);
    assertEquals(cardCell1.hashCode(), cardCell2.hashCode());
  }

  @Test
  public void testHashCodeDifferentObjects() {
    Card card1 = new Card("MysticEagle", 5, 10, 4, 2);
    Card card2 = new Card("DragonWarrior", 5, 10, 4, 2);
    IPlayer player1 = new HumanPlayer("Player1", List.of(card1));
    IPlayer player2 = new HumanPlayer("Player2", List.of(card2));
    CardCell cardCell1 = new CardCell(card1, player1);
    CardCell cardCell2 = new CardCell(card2, player2);
    assertNotEquals(cardCell1.hashCode(), cardCell2.hashCode());
  }
}
