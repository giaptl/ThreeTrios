package model;

/**
 * Represents the grid of the game.
 */
public class Grid {

  private Cell[][] cells;

  // Default if given no config file but given row and col
  public Grid(int row, int col) {
    cells = new Cell[row][col];
      for (int rows = 0; rows < row; rows++) {
        for (int cols = 0; cols < col; cols++) {
          // if hole then create an object of class hole
          // if not hole then create an object of class CardCell
          cells[rows][cols] = new CardCell();
        }
      }
  }

  // Constructor that will create a grid based on config file details


  /**
   * Returns the number of rows in the grid
   * @return the number of rows in the grid
   */
  public int getRows() {
    return cells.length;
  }

  /**
   * Returns the number of columns in the grid
   * @return the number of columns in the grid
   */
  public int getColumns() {
    return cells[0].length;
  }

  /**
   * Returns the cell at the given row and column
   * @param row the row of the cell
   * @param col the column of the cell
   * @return the cell at the given row and column
   */
  public Cell getCell(int row, int col) {
    return cells[row][col];
  }

  /**
   * Sets the cell at the given row and column
   * @param row the row of the cell
   * @param col the column of the cell
   * @param cellToSet the cell to set at the given row and column
   */
  public void setCell(int row, int col, Cell cellToSet) {
    cells[row][col] = cellToSet;
  }

  /**
   * Returns the number of cells that are not holes
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
}
