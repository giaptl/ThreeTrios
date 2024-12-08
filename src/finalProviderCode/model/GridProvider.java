package finalProviderCode.model;

/**
 * Represents the game grid, which consists of cells arranged in rows and columns.
 * The grid manages the placement of cards, checks for grid fullness, and counts the
 * cards owned by a specific player.
 */
public class GridProvider {

  /** A 2D array of {@link CellInterfaceProvider} objects representing the grid. */
  private final CellInterfaceProvider[][] cells;
  private final int rows;
  private final int cols;

  /**
   * Constructs a new {@link GridProvider} with the specified number of rows and columns.
   *
   * @param rows the number of rows in the grid
   * @param cols the number of columns in the grid
   */
  public GridProvider(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Rows and columns must be greater than zero.");
    }
    this.rows = rows;
    this.cols = cols;
    cells = new EmptyCellProvider[rows][cols];
    initializeGrid();
  }

  /**
   * Initializes the grid by filling it with empty {@link CellInterfaceProvider} objects.
   */
  private void initializeGrid() {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        cells[r][c] = new EmptyCellProvider();
      }
    }
  }

  /**
   * Returns the number of rows in the grid.
   *
   * @return the number of rows
   */
  public int getRows() {
    return rows;
  }

  /**
   * Returns the number of columns in the grid.
   *
   * @return the number of columns
   */
  public int getCols() {
    return cols;
  }

  /**
   * Places a card at the specified position in the grid if the cell is empty.
   *
   * @param row the row index where the card is to be placed
   * @param col the column index where the card is to be placed
   * @param card the card to be placed
   * @param owner the owner of the card
   * @return true if the card was placed successfully, false otherwise
   */
  public boolean placeCard(int row, int col, CardProvider card, String owner) {
    if (cells[row][col].isEmpty()) {
      cells[row][col].setCard(card, owner);
      return true;
    }
    return false;
  }

  /**
   * Checks if the grid is full (i.e., all cells are occupied).
   *
   * @return true if the grid is full, false otherwise
   */
  public boolean isFull() {
    for (CellInterfaceProvider[] row : cells) {
      for (CellInterfaceProvider cell : row) {
        if (cell.isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Counts the number of cards owned by a specific player in the grid.
   *
   * @param owner the owner whose cards to count
   * @return the number of cards owned by the specified player
   */
  public int countOwnedCards(String owner) {
    int count = 0;
    for (CellInterfaceProvider[] row : cells) {
      for (CellInterfaceProvider cell : row) {
        if (owner.equals(cell.getOwner())) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Renders the grid as a string representation.
   *
   * @return a string representing the current state of the grid
   */
  public String render() {
    StringBuilder sb = new StringBuilder();
    for (CellInterfaceProvider[] row : cells) {
      for (CellInterfaceProvider cell : row) {
        sb.append(cell.render()).append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Checks if a cell at the specified position is empty.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return true if the cell is empty, false otherwise
   */
  public boolean isCellEmpty(int row, int col) {
    return cells[row][col].isEmpty(); // Assumes `Cell` has an `isEmpty()` method
  }

  /**
   * Returns the owner of the cell at the specified position.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the owner of the cell (e.g., "RED", "BLUE"), or null if the cell is empty
   */
  public String getCellOwner(int row, int col) {
    return cells[row][col].getOwner();
  }

  /**
   * Gets the card at the specified position in the grid.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the card at the specified position, or null if no card is present
   */
  public Boolean getCardAt(int row, int col) {
    return null; // Placeholder; should return actual card object if implemented
  }

  /**
   * Checks if a cell at the specified position is occupied.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return true if the cell is occupied, false otherwise
   */
  public boolean isOccupied(int row, int col) {
    return cells[row][col] != null;
  }

  /**
   * Retrieves the cell at the specified position in the grid.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the {@link CellInterfaceProvider} object at the specified position
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  public CellInterfaceProvider getCell(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid row or column.");
    }
    return cells[row][col];
  }

}
