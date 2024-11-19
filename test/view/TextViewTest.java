package view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import configuration.ConfigurationReader;
import model.Card;
import model.Grid;
import model.HumanPlayer;
import model.IPlayer;

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
    Grid grid = ConfigurationReader.readGridConfig("src" + File.separator + "configuration"
            + File.separator + "configFiles" + File.separator + "board.config");
    TextView view = new TextView();
    view.renderGrid(grid);
    String expectedOutput = "__    _\n"
            + "_ _   _\n"
            + "_  _  _\n"
            + "_   _ _\n"
            + "_    __\n";
    String actualOutput = outContent.toString();
    // Normalize line separators
    expectedOutput = expectedOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRenderGridForBoard1() throws IOException {
    Grid grid = ConfigurationReader.readGridConfig(
            "src/configuration/configFiles/board1WithNoHoles.config");
    TextView view = new TextView();
    view.renderGrid(grid);
    String expectedOutput = "___\n"
                          + "___\n"
                          + "___\n";
    String actualOutput = outContent.toString();
    // Normalize line separators
    expectedOutput = expectedOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRenderGridForBoard2() throws IOException {
    Grid grid = ConfigurationReader.readGridConfig(
            "src/configuration/configFiles/board2WithReachableCells.config");
    TextView view = new TextView();
    view.renderGrid(grid);
    String expectedOutput = "____\n"
                          + "_  _\n"
                          + "____\n";
    String actualOutput = outContent.toString();
    // Normalize line separators ( IF ON MAC COMMENT THESE TWO LINES BELOW OUT)
    expectedOutput = expectedOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRenderGridForBoard3() throws IOException {
    Grid grid = ConfigurationReader.readGridConfig(
            "src/configuration/configFiles/board3WithSeparateGroupsOfCells.config");
    TextView view = new TextView();
    view.renderGrid(grid);
    String expectedOutput = "____    \n"
                          + "____ ___\n"
                          + "     ___\n"
                          + "___     \n";
    String actualOutput = outContent.toString();
    // Normalize line separators
    expectedOutput = expectedOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRenderPlayerHand() {
    IPlayer player = new HumanPlayer("TestPlayer", List.of());
    List<Card> hand = Arrays.asList(
            new Card("Card1", 1, 2, 3, 4),
            new Card("Card2", 5, 6, 7, 8)
    );

    TextView view = new TextView();
    view.renderPlayerHand(player, hand);

    String expectedOutput = "Player: TESTPLAYER\nHand:\nCard1 1 2 3 4\nCard2 5 6 7 8\n\n";
    String actualOutput = outContent.toString();
    // Normalize line separators
    expectedOutput = expectedOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n")
            .replace("\r", "\n");
    assertEquals(expectedOutput, actualOutput);
  }
}
