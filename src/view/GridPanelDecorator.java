package view;

import javax.swing.JPanel;

/**
 * Represents a decorator for a JPanel in a grid panel.
 */
public interface GridPanelDecorator {
  void decorate(JPanel cellPanel, int row, int col);
}