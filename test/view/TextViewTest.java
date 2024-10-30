package view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import configuration.ConfigurationReader;
import model.Card;
import model.Grid;
import model.Player;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the text view class in view.
 */
public class TextViewTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @Before
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testRenderGridForExampleBoardN() throws IOException {
    Grid grid = ConfigurationReader.readGridConfig("configFiles/board.config");
    TextView view = new TextView();
    view.renderGrid(grid);
    String expectedOutput = "__    _\n"
                          + "_ _   _\n"
                          + "_  _  _\n"
                          + "_   _ _\n"
                          + "_    __\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testRenderGridForBoard1() throws IOException {
    Grid grid = ConfigurationReader.readGridConfig("configFiles/board1WithNoHoles.config");
    TextView view = new TextView();
    view.renderGrid(grid);
    String expectedOutput = "___\n"
                          + "___\n"
                          + "___\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testRenderGridForBoard2() throws IOException {
    Grid grid = ConfigurationReader.readGridConfig(
            "configFiles/board2WithReachableCells.config");
    TextView view = new TextView();
    view.renderGrid(grid);
    String expectedOutput = "____\n"
                          + "_  _\n"
                          + "____\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testRenderGridForBoard3() throws IOException {
    Grid grid = ConfigurationReader.readGridConfig(
            "configFiles/board3WithSeparateGroupsOfCells.config");
    TextView view = new TextView();
    view.renderGrid(grid);
    String expectedOutput = "____    \n"
                          + "____ ___\n"
                          + "     ___\n"
                          + "___     \n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testRenderPlayerHand() {
    Player player = new Player("TestPlayer", List.of());
    List<Card> hand = Arrays.asList(
            new Card("Card1", 1, 2, 3, 4),
            new Card("Card2", 5, 6, 7, 8)
    );

    TextView view = new TextView();
    view.renderPlayerHand(player, hand);

    String expectedOutput = "Player: TESTPLAYER\nHand:\nCard1 1 2 3 4\nCard2 5 6 7 8\n\n";
    assertEquals(expectedOutput, outContent.toString());
  }
}
