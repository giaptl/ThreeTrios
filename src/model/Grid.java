package model;

public class Grid {

  private Cell[][] cells;



  // Default if given no config file but given row and col
  public Grid(int row, int col, boolean isConfigFile) {
    cells = new Cell[row][col];

    if (!isConfigFile) {
      for (int rows = 0; rows < row; rows++) {
        for (int cols = 0; cols < col; cols++) {
          // if hole then create an object of class hole
          // if not hole then create an object of class CardCell
          cells[rows][cols] = new CardCell();
        }
      }
    }
  }

  // Constructor that will create a grid based on config file details


  public int getRows() {
    return cells.length;
  }

  public int getColumns() {
    return cells[0].length;
  }

  protected Cell getCell(int row, int col) {
    return cells[row][col];
  }

  public void SetCell(int row, int col, Cell cellToSet) {
    cells[row][col] = cellToSet;
  }

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
