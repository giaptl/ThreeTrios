package model;

/**
 * Represents the grid of the game.
 * Grid Structure:
 *  - The grid is rectangular, with rows and columns specified in the configuration.
 *  - Each cell can be either a card cell (playable) or a hole (non-playable).
 *  - The number of card cells must be odd.
 *  - Rows and Cols start at index 0.
 */
public class Grid {

  private Cell[][] cells;

  /**
   * Default if given no config file but given row and col.
   * Not used yet (Waiting for further implementation).
   */
  public Grid(int row, int col) {
    cells = new Cell[row][col];
    for (int rows = 0; rows < row; rows++) {
      for (int cols = 0; cols < col; cols++) {
        cells[rows][cols] = new CardCell();
      }
    }
  }



  /**
   * Returns the number of rows in the grid.
   * @return the number of rows in the grid
   */
  public int getRows() {
    return cells.length;
  }

  /**
   * Returns the number of columns in the grid.
   * @return the number of columns in the grid
   */
  public int getColumns() {
    return cells[0].length;
  }

  /**
   * Returns the cell at the given row and column.
   * @param row the row of the cell
   * @param col the column of the cell
   * @return the cell at the given row and column
   * @throws IllegalArgumentException if the row or column is out of bounds
   */
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= cells.length || col < 0 || col >= cells[0].length) {
      throw new IllegalArgumentException("Index out of bounds: row " + row + ", col " + col);
    }
    return cells[row][col];
  }

  /**
   * Sets the cell at the given row and column.
   * @param row the row of the cell
   * @param col the column of the cell
   * @param cellToSet the cell to set at the given row and column
   */
  public void setCell(int row, int col, Cell cellToSet) {
    cells[row][col] = cellToSet;
  }

  public void setGrid(Grid newGrid) {
    if (newGrid.getRows() != getRows() || newGrid.getColumns() != getColumns()) {
      throw new IllegalArgumentException("New grid dimensions must match current grid dimensions");
    }
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getColumns(); col++) {
        this.cells[row][col] = newGrid.getCell(row, col);
      }
    }
  }

  /**
   * Returns the number of cells that are not holes.
   * @return the number of cells that are not holes
   */
  public int getNumCardCells() {
    int count = 0;
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getColumns(); col++) {
        if (!cells[row][col].isHole()) {
          count++;
        }
      }
    }
    return count;
  }

  protected Grid copyOfGrid() {
    Grid newGrid = new Grid(this.getRows(), this.getColumns());
    for (int row = 0; row < this.getRows(); row++) {
      for (int col = 0; col < this.getColumns(); col++) {
        Cell originalCell = this.getCell(row, col);
        if (originalCell.isHole()) {
          newGrid.setCell(row, col, new Hole());
        } else {
          CardCell originalCardCell = (CardCell) originalCell;
          newGrid.setCell(row, col, new CardCell(originalCardCell.getCard(), originalCardCell.getOwner()));
        }
      }
    }
    return newGrid;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getColumns(); col++) {
        Cell cell = getCell(row, col);
        if (cell.isHole()) {
          sb.append("H ");
        } else {
          CardCell cardCell = (CardCell) cell;
          sb.append(cardCell.getCard() != null ? "C " : "E ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }


}
