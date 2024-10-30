package model;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    Player player = new Player("TestPlayer", List.of(card));
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
    Player player1 = new Player("Player1", List.of(card));
    Player player2 = new Player("Player2", List.of(card));
    CardCell cell = new CardCell(card, player1);

    assertEquals(player1, cell.getOwner());
    cell.setOwner(player2);
    assertEquals(player2, cell.getOwner());
  }
}
