package model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the Grid class in model.
 */
public class GridTest {

  @Test
  public void testGridCreation() {
    Grid grid = new Grid(3, 3);
    assertNotNull(grid);
    assertEquals(3, grid.getRows());
    assertEquals(3, grid.getColumns());
  }

  @Test
  public void testGetCell() {
    Grid grid = new Grid(3, 3);
    Cell cell = grid.getCell(0, 0);
    assertNotNull(cell);
    assertTrue(cell instanceof CardCell);
  }

  @Test
  public void testSetCell() {
    Grid grid = new Grid(3, 3);
    Cell newCell = new CardCell();
    grid.setCell(0, 0, newCell);
    assertEquals(newCell, grid.getCell(0, 0));
  }

  @Test
  public void testGetNumCardCells() {
    Grid grid = new Grid(3, 3);
    assertEquals(9, grid.getNumCardCells());
  }

  @Test
  public void testGetNumCardCellsWithHoles() {
    Grid grid = new Grid(3, 3);
    grid.setCell(0, 0, new Hole());
    assertEquals(8, grid.getNumCardCells());
  }

  @Test
  public void testCopyOfGridSameContent() {
    Grid originalGrid = new Grid(3, 3);
    originalGrid.setCell(0, 0, new CardCell(
            new Card("Card1", 1, 2, 3, 4),
            new HumanPlayer("Player1",
                    List.of(new Card("Card1", 1, 2, 3, 4)))));
    originalGrid.setCell(1, 1, new Hole());
    originalGrid.setCell(2, 2, new CardCell(
            new Card("Card2", 5, 6, 7, 8),
            new HumanPlayer("Player2",
                    List.of(new Card("Card2", 5, 6, 7, 8)))));

    Grid copiedGrid = originalGrid.copyOfGrid();

    for (int row = 0; row < originalGrid.getRows(); row++) {
      for (int col = 0; col < originalGrid.getColumns(); col++) {
        assertEquals(originalGrid.getCell(row, col), copiedGrid.getCell(row, col));
      }
    }
  }

  @Test
  public void testCopyOfGridDifferentInstance() {
    Grid originalGrid = new Grid(3, 3);
    originalGrid.setCell(0, 0, new CardCell(
            new Card("Card1", 1, 2, 3, 4),
            new HumanPlayer("Player1",
                    List.of(new Card("Card1", 1, 2, 3, 4)))));
    originalGrid.setCell(1, 1, new Hole());
    originalGrid.setCell(2, 2, new CardCell(
            new Card("Card2", 5, 6, 7, 8),
            new HumanPlayer("Player2",
                    List.of(new Card("Card2", 5, 6, 7, 8)))));

    Grid copiedGrid = originalGrid.copyOfGrid();

    assertNotSame(originalGrid, copiedGrid);
    for (int row = 0; row < originalGrid.getRows(); row++) {
      for (int col = 0; col < originalGrid.getColumns(); col++) {
        assertNotSame(originalGrid.getCell(row, col), copiedGrid.getCell(row, col));
      }
    }
  }

  @Test
  public void testCopyOfGridModifyOriginal() {
    Grid originalGrid = new Grid(3, 3);
    originalGrid.setCell(0, 0, new CardCell(
            new Card("Card1", 1, 2, 3, 4),
            new HumanPlayer("Player1",
                    List.of(new Card("Card1", 1, 2, 3, 4)))));
    originalGrid.setCell(1, 1, new Hole());
    originalGrid.setCell(2, 2, new CardCell(
            new Card("Card2", 5, 6, 7, 8),
            new HumanPlayer("Player2",
                    List.of(new Card("Card2", 5, 6, 7, 8)))));

    Grid copiedGrid = originalGrid.copyOfGrid();

    originalGrid.setCell(0, 0, new Hole());

    assertNotEquals(originalGrid.getCell(0, 0), copiedGrid.getCell(0, 0));
  }
}
