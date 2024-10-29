package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {
  @Test
  public void testGetRowOffset() {
    assertEquals(-1, Direction.NORTH.getRowOffset());
    assertEquals(1, Direction.SOUTH.getRowOffset());
    assertEquals(0, Direction.EAST.getRowOffset());
    assertEquals(0, Direction.WEST.getRowOffset());
  }

  @Test
  public void testGetColOffset() {
    assertEquals(0, Direction.NORTH.getColOffset());
    assertEquals(0, Direction.SOUTH.getColOffset());
    assertEquals(1, Direction.EAST.getColOffset());
    assertEquals(-1, Direction.WEST.getColOffset());
  }

  @Test
  public void testGetOpposite() {
    assertEquals(Direction.SOUTH, Direction.NORTH.getOpposite());
    assertEquals(Direction.NORTH, Direction.SOUTH.getOpposite());
    assertEquals(Direction.WEST, Direction.EAST.getOpposite());
    assertEquals(Direction.EAST, Direction.WEST.getOpposite());
  }
}
