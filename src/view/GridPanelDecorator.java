package view;

import javax.swing.JPanel;

public interface GridPanelDecorator {
  void decorate(JPanel cellPanel, int row, int col);
}