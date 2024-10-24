package model;

public class Grid {

  private Cell[][] cells;


  public Grid() {
    cells[1][2] = new Hole();
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

  }

  public int getCols() {
  }
}
