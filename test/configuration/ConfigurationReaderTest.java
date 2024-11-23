package configuration;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.Card;
import model.CardCell;
import model.Direction;
import model.Grid;
import model.Hole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Test class for ConfigurationReader.
 */
public class ConfigurationReaderTest {

  // Load data from board.config
  @Test
  public void testReadBoardConfig() throws IOException {
    String gridConfigPath = "src" + File.separator + "configuration"
            + File.separator + "configFiles" + File.separator + "board.config";
    Grid grid = ConfigurationReader.readGridConfig(gridConfigPath);
    assertNotNull(grid);
    assertEquals(5, grid.getRows());
    assertEquals(7, grid.getColumns());
    assertFalse(grid.getCell(1, 0) instanceof Hole);
    assertFalse(grid.getCell(0, 0) instanceof Hole);
    assertFalse(grid.getCell(1, 2) instanceof Hole);
    assertTrue(grid.getCell(0, 0) instanceof CardCell);
  }

  // Load data from board1WithNoHoles.config
  @Test
  public void testReadGridConfig() throws IOException {
    String gridConfigPath = "src" + File.separator + "configuration"
            + File.separator + "configFiles" + File.separator + "board1WithNoHoles.config";
    Grid grid = ConfigurationReader.readGridConfig(gridConfigPath);
    assertNotNull(grid);
    assertEquals(3, grid.getRows());
    assertEquals(3, grid.getColumns());
    assertFalse(grid.getCell(1, 1) instanceof Hole);
    assertFalse(grid.getCell(0, 0) instanceof Hole);
    assertFalse(grid.getCell(1, 2) instanceof Hole);
    assertTrue(grid.getCell(0, 0) instanceof CardCell);
  }

  // Load data from board2WithReachableCells.config
  @Test
  public void testReadGridConfig2() throws IOException {
    String gridConfigPath = "src" + File.separator + "configuration"
            + File.separator + "configFiles" + File.separator + "board2WithReachableCells.config";
    Grid grid = ConfigurationReader.readGridConfig(gridConfigPath);
    assertNotNull(grid);
    assertEquals(3, grid.getRows());
    assertEquals(4, grid.getColumns());
    assertFalse(grid.getCell(1, 2) instanceof Hole);
    assertTrue(grid.getCell(1, 1) instanceof Hole);
    assertFalse(grid.getCell(1, 0) instanceof Hole);
    assertTrue(grid.getCell(0, 0) instanceof CardCell);
  }

  // Load data from board3WithSeparateGroupsOfCells.config
  @Test
  public void testReadGridConfig3() throws IOException {
    String gridConfigPath = "src" + File.separator + "configuration"
            + File.separator + "configFiles" + File.separator
            + "board3WithSeparateGroupsOfCells.config";
    Grid grid = ConfigurationReader.readGridConfig(gridConfigPath);
    assertNotNull(grid);
    assertEquals(4, grid.getRows());
    assertEquals(8, grid.getColumns());
    assertTrue(grid.getCell(0, 6) instanceof Hole);
    assertTrue(grid.getCell(2, 2) instanceof Hole);
    assertFalse(grid.getCell(1, 0) instanceof Hole);
    assertTrue(grid.getCell(0, 0) instanceof CardCell);
  }

  // Load data from cardsEnoughForAllBoards.config
  @Test
  public void testReadCardData() throws IOException {
    String cardDataPath = "src" + File.separator + "configuration"
            + File.separator + "configFiles" + File.separator + "cardsEnoughForAllBoards.config";
    List<Card> deck = ConfigurationReader.readCardData(cardDataPath);

    assertEquals(36, deck.size());

    // Check the first card was loaded properly
    Card firstCard = deck.get(0);
    assertEquals("CorruptKing", firstCard.getName());
    assertEquals("7", firstCard.getAttackValue(Direction.NORTH));
    assertEquals("3", firstCard.getAttackValue(Direction.SOUTH));
    assertEquals("9", firstCard.getAttackValue(Direction.EAST));
    assertEquals("A", firstCard.getAttackValue(Direction.WEST));

    // Check the last card was loaded properly
    Card lastCard = deck.get(deck.size() - 1);
    assertEquals("RubyTiger", lastCard.getName());
    assertEquals("8", lastCard.getAttackValue(Direction.NORTH));
    assertEquals("5", lastCard.getAttackValue(Direction.SOUTH));
    assertEquals("A", lastCard.getAttackValue(Direction.EAST));
    assertEquals("6", lastCard.getAttackValue(Direction.WEST));
  }
}
