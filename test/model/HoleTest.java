package model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HoleTest {

  @Test
  public void testHoleCreation() {
    Hole hole = new Hole();
    assertNotNull(hole);
  }

  @Test
  public void testIsHole() {
    Hole hole = new Hole();
    assertTrue(hole.isHole());
  }
}
