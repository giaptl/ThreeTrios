package model;

public class Grid {

  private Cell[][] cells;


  public Grid(int row, int col) {
    cells = new Cell[row][col];

    for (int rows = 0; rows < row; rows++) {
      for (int cols = 0; cols < col; cols++) {
        cells[rows][cols] = new CardCell();
      }
    }

  }

  public Grid(boolean[][] holePositions) {
    int rows = holePositions.length;
    int cols = holePositions[0].length;
    cells = new Cell[rows][cols];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (holePositions[row][col]) {
          cells[row][col] = new Hole();
        } else {
          cells[row][col] = new CardCell();
        }
      }
    }
  }


  protected int getRows() {
    return cells.length;
  }

  protected int getColumns() {
    return cells[0].length;
  }

  protected Cell getCell(int row, int col) {
    return cells[row][col];
  }

  protected void SetCell(int row, int col, Cell cellToSet) {
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
