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

public class ConfigurationReader {

  public static Grid readGridConfig(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String[] dimensions = reader.readLine().split(" ");
      int rows = Integer.parseInt(dimensions[0]);
      int cols = Integer.parseInt(dimensions[1]);

      Grid grid = new Grid(rows, cols, true);

      for (int row = 0; row < rows; row++) {
        String line = reader.readLine();
        for (int col = 0; col < cols; col++) {
          char cellType = line.charAt(col);
          if (cellType == 'X') {
            grid.SetCell(row, col, new Hole());
          } else if (cellType == 'C') {
            grid.SetCell(row, col, new CardCell());
          } else {
            throw new IllegalArgumentException("Invalid cell type: " + cellType
                    + " at row " + row + ", col " + col);
          }
        }
      }

      return grid;
    }
  }

  public static List<Card> readCardData(String filename) throws IOException {
    List<Card> cards = new ArrayList<>();
    Set<String> cardNames = new HashSet<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(" ");
        String name = parts[0];
        if (!cardNames.add(name)) {
          throw new IllegalArgumentException("Duplicate card name found: " + name);
        }
        int north = Integer.parseInt(parts[1]);
        int south = Integer.parseInt(parts[2]);
        int east = Integer.parseInt(parts[3]);
        int west = Integer.parseInt(parts[4]);
        cards.add(new Card(name, north, south, east, west));
      }
    }
    return cards;
  }
}