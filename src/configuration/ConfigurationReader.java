package configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Card;
import model.CardCell;
import model.Grid;
import model.Hole;

/**
 * Reads configuration files for the grid and card data. Very important for reading the starting
 * files to start the game.
 */
public class ConfigurationReader {

  /**
   * Reads the grid configuration from the given file to use in the game.
   * Configuration file format:
   * - First line: two integers separated by a space representing the number of rows and columns
   * - Next lines: each row of the grid represented by a string of characters (X or C)
   *  - X represents a hole
   *  - C represents a card cell
   *  - Any other character is invalid
   * - The number of rows and columns must match the dimensions of the grid.
   * - The number of rows and columns must be positive integers.
   * - The grid must contain at least one card cell.
   * - The grid must be a rectangle.
   * NOTE: Configuration files must be stored in the configFiles directory.
   *
   *
   * @param filename the name of the file to parse data from
   * @return the grid configuration created based on file contents
   * @throws IOException if an error occurs while reading the file
   */
  public static Grid readGridConfig(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String[] dimensions = reader.readLine().split(" ");
      int rows = Integer.parseInt(dimensions[0]);
      int cols = Integer.parseInt(dimensions[1]);

      Grid grid = new Grid(rows, cols);

      for (int row = 0; row < rows; row++) {
        String line = reader.readLine();
        for (int col = 0; col < cols; col++) {
          char cellType = line.charAt(col);
          if (cellType == 'X') {
            grid.setCell(row, col, new Hole());
          } else if (cellType == 'C') {
            grid.setCell(row, col, new CardCell());
          } else {
            throw new IllegalArgumentException("Invalid cell type: " + cellType
                    + " at row " + row + ", col " + col);
          }
        }
      }

      return grid;
    }
  }

  /**
   * Reads the card configuration from the given file to use in the game. Also check that all cards
   * are unique.
   * Configuration file format:
   * - Each line represents a card with the following format:
   * - Card name followed by four integers separated by spaces representing the attack values
   *  for each direction (N, S, E, W)
   *  - The card name must be unique
   *  - The attack values must be integers between 1 and 10 or 'A' for 10
   *  - The card name and attack values must be separated by a space
   *  - The card name must not contain spaces
   *  - The card name must not be empty
   *
   * @param filename the name of the file to parse data from
   * @return list of cards created based on file contents
   * @throws IOException if an error occurs while reading the file
   */
  public static List<Card> readCardData(String filename) throws IOException {
    List<Card> cards = new ArrayList<>();
    Set<String> cardNames = new HashSet<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(" ");
        String name = parts[0];
        // Check for duplicate card names
        if (!cardNames.add(name)) {
          throw new IllegalArgumentException("Duplicate card name found: " + name);
        }
        // Check that the card data is in the correct format
        if (parts.length != 5) {
          throw new IllegalArgumentException("Invalid card data format");
        }

        int north = parts[1].equals("A") ? 10 : Integer.parseInt(parts[1]);
        int south = parts[2].equals("A") ? 10 : Integer.parseInt(parts[2]);
        int east = parts[3].equals("A") ? 10 : Integer.parseInt(parts[3]);
        int west = parts[4].equals("A") ? 10 : Integer.parseInt(parts[4]);
        cards.add(new Card(name, north, south, east, west));
      }
    }
    return cards;
  }
}