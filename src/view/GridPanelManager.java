package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import controller.Controller;
import model.Card;
import model.ReadOnlyThreeTriosModel;

public class GridPanelManager {
  private final ReadOnlyThreeTriosModel model;
  private Controller controller;
  private final JPanel gridPanel;

  public GridPanelManager(ReadOnlyThreeTriosModel model) {
    this.model = model;
    this.gridPanel = createGridPanel();
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public JPanel getGridPanel() {
    return gridPanel;
  }

  private JPanel createGridPanel() {
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getColumns();
    JPanel panel = new JPanel(new GridLayout(rows, cols));

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        JPanel cellPanel = new JPanel();
        cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cellPanel.setBackground(model.getGrid().getCell(row, col).isHole() ? Color.GRAY : Color.YELLOW);

        int finalRow = row;
        int finalCol = col;

        cellPanel.addMouseListener(new java.awt.event.MouseAdapter() {
          @Override
          public void mouseClicked(java.awt.event.MouseEvent e) {
            controller.handleGridClick(finalRow, finalCol);
          }
        });

        panel.add(cellPanel);
      }
    }

    return panel;
  }

  public void updateGridPanel() {
    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getColumns(); col++) {
        JPanel cellPanel = (JPanel) gridPanel.getComponent(row * model.getGrid().getColumns() + col);
        cellPanel.removeAll();
        cellPanel.setLayout(new BorderLayout());

        Card card = model.getGrid().getCell(row, col).getCard();
        if (card != null) {
          createCardPanel(row, col, cellPanel, card);
        } else {
          cellPanel.setBackground(model.getGrid().getCell(row, col).isHole() ? Color.GRAY : Color.YELLOW);
        }

        cellPanel.revalidate();
        cellPanel.repaint();
      }
    }
  }

  private void createCardPanel(int row, int col, JPanel cellPanel, Card card) {
    CardPanel cardPanel = CardPanelFactory.createCardPanel(
            card,
            model.getGrid().getCell(row, col).getOwner().equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN
    );
    cellPanel.add(cardPanel, BorderLayout.CENTER);
  }
}