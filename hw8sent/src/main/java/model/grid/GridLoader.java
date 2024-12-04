package model.grid;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A utility class for loading a {@link Grid} from a configuration file.
 * The configuration file specifies the grid's dimensions and structure.
 */
public class GridLoader {

  /**
   * Loads a {@link Grid} from the specified file.
   * The file should contain the number of rows and columns on the first line,
   * followed by rows of characters representing the grid cells.
   *
   * <ul>
   *   <li>'X' - A hole in the grid (unusable cell)</li>
   *   <li>'C' - A card cell where cards can be placed</li>
   * </ul>
   *
   * @param filename the path to the grid configuration file
   * @return a {@link Grid} object initialized based on the file, or
   * {@code null} if the file is not found
   */
  public static Grid loadGrid(String filename) {
    try (Scanner scanner = new Scanner(new File(filename))) {
      int rows = scanner.nextInt();
      int cols = scanner.nextInt();
      scanner.nextLine();


      Grid grid = new Grid(rows, cols);


      for (int r = 0; r < rows; r++) {
        String line = scanner.nextLine();
        for (int c = 0; c < cols; c++) {
          char cell = line.charAt(c);

          if (cell == 'X') {

            continue;
          } else if (cell == 'C') {

            grid.placeCard(r, c, null, "");
          }
        }
      }
      return grid;

    } catch (FileNotFoundException e) {

      System.err.println("Grid file not found: " + filename);
      return null;
    }
  }
}
