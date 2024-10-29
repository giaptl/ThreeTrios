package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
}
