package view;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


import controller.Controller;
import model.Card;
import model.Direction;
import model.Player;
import model.ReadOnlyThreeTriosModel;

public class GameView extends JFrame implements IGameView {
  private final ReadOnlyThreeTriosModel model;
  private Controller controller;
  private JPanel gridPanel;
  private JPanel redHandPanel;
  private JPanel blueHandPanel;
  private JPanel previouslySelectedCardPanel = null;


  public GameView(ReadOnlyThreeTriosModel model) {
    this.model = model;
    setTitle("ThreeTrios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Create panels for hands and grid
    redHandPanel = createHandPanel(model.getRedPlayer());
    blueHandPanel = createHandPanel(model.getBluePlayer());
    gridPanel = createGridPanel();

    // Add panels to frame
    add(redHandPanel, BorderLayout.WEST);
    add(blueHandPanel, BorderLayout.EAST);
    add(gridPanel, BorderLayout.CENTER);

    pack(); // Adjusts window size based on component sizes
    setVisible(true);
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }


  private JPanel createHandPanel(Player player) {
    JPanel handPanel = new JPanel();
    handPanel.setLayout(new GridLayout(player.getHand().size(), 1));
    Color backgroundColor = player.equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN;

    List<Card> hand = model.getPlayerHand(player);
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);

      // Create a CardPanel for each card with its values
      CardPanel cardPanel = new CardPanel(
              card.getAttackValue(Direction.NORTH),
              card.getAttackValue(Direction.SOUTH),
              card.getAttackValue(Direction.EAST),
              card.getAttackValue(Direction.WEST),
              backgroundColor);

      // Add mouse listener to handle clicks on cards
      int index = i;
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          controller.handleCardClick(player, index);
        }
      });

      handPanel.add(cardPanel);
    }

    return handPanel;
  }

  /**
   * Creates a panel to display the game grid.
   */
  private JPanel createGridPanel() {
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getColumns();

    JPanel panel = new JPanel(new GridLayout(rows, cols));

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        JPanel cellPanel = new JPanel();
        cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cellPanel.setBackground(model.getGrid()
                .getCell(row, col).isHole() ? Color.GRAY : Color.YELLOW);

        int finalRow = row;
        int finalCol = col;

        cellPanel.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            controller.handleGridClick(finalRow, finalCol);
          }
        });

        panel.add(cellPanel);
      }
    }

    return panel;
  }


  @Override
  public void updateCardSelection(Player player, Card card) {
    // Reset previous selection
    if (previouslySelectedCardPanel != null) {
      previouslySelectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      previouslySelectedCardPanel = null;
    }

    // If there's a new selection, highlight it
    if (player != null && card != null) {
      JPanel cardPanel;
      int cardIndex = model.getPlayerHand(player).indexOf(card);

      if (player.equals(model.getRedPlayer())) {
        cardPanel = (JPanel) redHandPanel.getComponent(cardIndex);
      } else {
        cardPanel = (JPanel) blueHandPanel.getComponent(cardIndex);
      }

      cardPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
      previouslySelectedCardPanel = cardPanel;
    }
  }


  @Override
  public void highlightSelectedCard(int cardIndex, Player player) {
    JPanel handPanel;
    // Get the correct hand panel
    if (player.equals(model.getRedPlayer())) {
      handPanel = (JPanel) redHandPanel.getComponent(cardIndex);
    } else {
      handPanel = (JPanel) blueHandPanel.getComponent(cardIndex);
    }

    // Reset border of the previously selected card panel
    if (previouslySelectedCardPanel != null) {
      previouslySelectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    // Highlight the current card panel by changing its border
    handPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
    previouslySelectedCardPanel = handPanel;

    handPanel.revalidate();
    handPanel.repaint();
  }


  @Override
  public void updateGridCell(int row, int col, Card card) {
    JPanel cellPanel = (JPanel) gridPanel.getComponent(row * model.getGrid().getColumns() + col);
    cellPanel.removeAll();
    cellPanel.setLayout(new BorderLayout());
    createCardPanel(row, col, cellPanel, card);
    cellPanel.revalidate();
    cellPanel.repaint();
  }

  @Override
  public void removeCardFromHandPanel(Player player, Card card) {
    JPanel handPanel = player.equals(model.getRedPlayer()) ? redHandPanel : blueHandPanel;
    List<Card> hand = model.getPlayerHand(player);

    for (int i = 0; i < hand.size(); i++) {
      if (hand.get(i).equals(card)) {
        handPanel.remove(i);
        break;
      }
    }
    handPanel.revalidate();
    handPanel.repaint();
  }

  @Override
  public void refreshView() {
    // Remove old grid panel
    remove(gridPanel);

    // Update the existing gridPanel
    updateGridPanel();

    // Update hand panels
    updateHandPanel(redHandPanel, model.getRedPlayer());
    updateHandPanel(blueHandPanel, model.getBluePlayer());

    // Add grid panel back to frame
    add(gridPanel, BorderLayout.CENTER);

    // Revalidate and repaint to update UI
    revalidate();
    repaint();
  }

  private void updateHandPanel(JPanel handPanel, Player player) {
    handPanel.removeAll();
    List<Card> hand = model.getPlayerHand(player);
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      CardPanel cardPanel = new CardPanel(
              card.getAttackValue(Direction.NORTH),
              card.getAttackValue(Direction.SOUTH),
              card.getAttackValue(Direction.EAST),
              card.getAttackValue(Direction.WEST),
              player.equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN
      );
      int index = i;
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          controller.handleCardClick(player, index);
        }
      });
      handPanel.add(cardPanel);
    }
    handPanel.revalidate();
    handPanel.repaint();
  }

  private void updateGridPanel() {
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
    CardPanel cardPanel = new CardPanel(
            card.getAttackValue(Direction.NORTH),
            card.getAttackValue(Direction.SOUTH),
            card.getAttackValue(Direction.EAST),
            card.getAttackValue(Direction.WEST),
            model.getGrid().getCell(row, col).getOwner().equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN
    );
    cellPanel.add(cardPanel, BorderLayout.CENTER);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(800, 600); // Default window size
  }
}