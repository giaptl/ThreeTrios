import model.cards.Card;
import model.grid.Grid;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Tests for the Grid class to ensure proper handling of grid operations and state.
 */
public class GridTest {

  /**
   * Tests if the grid is correctly initialized.
   */
  @Test
  public void testGridInitialization() {
    Grid grid = new Grid(3, 3);
    assertNotNull(grid);
  }

  /**
   * Tests rendering of an empty grid.
   */
  @Test
  public void testEmptyGridRender() {
    Grid grid = new Grid(2, 2);
    String expected = "_ _ \n_ _ \n";
    assertEquals(expected, grid.render());
  }

  /**
   * Tests successful placement of a card on an empty cell.
   */
  @Test
  public void testPlaceCardSuccess() {
    Grid grid = new Grid(3, 3);
    Card card = new Card("HeroKnight", 4, 5, 7, 3);
    assertTrue(grid.placeCard(1, 1, card, "RED"));
  }

  /**
   * Tests failure of placing a card on an occupied cell.
   */
  @Test
  public void testPlaceCardFailureOnOccupiedCell() {
    Grid grid = new Grid(3, 3);
    Card card = new Card("HeroKnight", 4, 5, 7, 3);
    grid.placeCard(1, 1, card, "RED");
    assertFalse(grid.placeCard(1, 1, new Card("FireDragon", 8, 3, 6, 2), "BLUE"));
  }

  /**
   * Tests if the grid is recognized as full when all cells are occupied.
   */
  @Test
  public void testGridFullCondition() {
    Grid grid = new Grid(2, 2);
    Card card = new Card("SkyWhale", 4, 3, 6, 7);
    grid.placeCard(0, 0, card, "RED");
    grid.placeCard(0, 1, card, "BLUE");
    grid.placeCard(1, 0, card, "RED");
    grid.placeCard(1, 1, card, "BLUE");
    assertTrue(grid.isFull());
  }

  /**
   * Tests if the grid is not recognized as full when some cells are empty.
   */
  @Test
  public void testGridNotFullCondition() {
    Grid grid = new Grid(2, 2);
    Card card = new Card("SkyWhale", 4, 3, 6, 7);
    grid.placeCard(0, 0, card, "RED");
    assertFalse(grid.isFull());
  }

  /**
   * Tests counting of cards owned by each player on the grid.
   */
  @Test
  public void testCountOwnedCards() {
    Grid grid = new Grid(3, 3);
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    grid.placeCard(0, 0, card, "RED");
    grid.placeCard(1, 1, card, "RED");
    grid.placeCard(2, 2, card, "BLUE");
    assertEquals(2, grid.countOwnedCards("RED"));
    assertEquals(1, grid.countOwnedCards("BLUE"));
  }

  /**
   * Tests rendering of the grid after placing a card.
   */
  @Test
  public void testGridRenderAfterPlacement() {
    Grid grid = new Grid(2, 2);
    Card card = new Card("FireDragon", 8, 3, 6, 2);
    grid.placeCard(0, 0, card, "RED");
    String expected = "R _ \n_ _ \n";
    assertEquals(expected, grid.render());
  }
}
