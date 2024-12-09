package adapters;

import finalProviderCode.model.CellInterfaceProvider;
import finalProviderCode.model.GridProvider;
import model.Grid;

public class GridAdapter extends GridProvider {
  private final Grid grid;

  public GridAdapter(Grid grid) {
    super(grid.getRows(), grid.getColumns());
    this.grid = grid;
  }

  @Override
  public int getRows() {
    return grid.getRows();
  }

  @Override
  public int getCols() {
    return grid.getColumns();
  }

  @Override
  public CellInterfaceProvider getCell(int row, int col) {
    return new CellAdapter(grid.getCell(row, col));
  }
}