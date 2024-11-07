package view;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


import model.Card;
import model.CardCell;
import model.Direction;
import model.Grid;
import model.Player;
import model.ReadOnlyThreeTriosModel;
import model.ThreeTriosModel;

public class GameView extends JFrame implements IGameView {
  private final ReadOnlyThreeTriosModel model;
  private final JPanel gridPanel;
  private final JPanel redHandPanel;
  private final JPanel blueHandPanel;
  private Card selectedCard = null;
  private Player selectedPlayer = null;
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
                .getCell(row, col).isHole() ? Color.YELLOW : Color.GRAY);

        int finalRow = row;
        int finalCol = col;

        cellPanel.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            handleGridClick(finalRow, finalCol);
          }
        });

        panel.add(cellPanel);
      }
    }

    return panel;
  }

  /**
   * Handles clicking on a card in a player's hand.
   */
  private void handleCardClick(Player player, int cardIndex) {
    JPanel currentCardPanel;
    if (player.equals(model.getRedPlayer())) {
      currentCardPanel = (JPanel) redHandPanel.getComponent(cardIndex);
    } else {
      currentCardPanel = (JPanel) blueHandPanel.getComponent(cardIndex);
    }

    if (selectedCard != null && selectedPlayer == player &&
            model.getPlayerHand(player).get(cardIndex).equals(selectedCard)) {
      // Deselect card if clicked again
      selectedCard = null;
      selectedPlayer = null;
      System.out.println("Deselected card from " + player.getName());

      // Reset border of the previously selected card panel
      if (previouslySelectedCardPanel != null) {
        previouslySelectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        previouslySelectedCardPanel = null;
      }
    } else {
      // Select new card
      selectedCard = model.getPlayerHand(player).get(cardIndex);
      selectedPlayer = player;
      System.out.println("Selected card " + selectedCard.getName()
              + " from " + player.getName() + " at index: " + cardIndex);

      // Reset border of the previously selected card panel
      if (previouslySelectedCardPanel != null) {
        previouslySelectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      }

      // Highlight the current card panel by changing its border
      currentCardPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
      previouslySelectedCardPanel = currentCardPanel;
    }

    // Optionally update UI to highlight selected card
  }

  /**
   * Handles clicking on a grid cell.
   */
  private void handleGridClick(int row, int col) {
    System.out.println("Clicked on grid cell at (" + row + ", " + col + ")");

    if (selectedCard != null && selectedPlayer != null) {
      // Check if the cell is a valid CardCell
      if (model.getGrid().getCell(row, col) instanceof CardCell) {
        // Cast model to ThreeTriosModel to access playCard method
        if (model instanceof ThreeTriosModel) {
          ThreeTriosModel gameModel = (ThreeTriosModel) model;

          // Place the selected card on the grid
          gameModel.playCard(selectedPlayer, selectedCard, row, col);

          // Remove the card from the player's hand
          gameModel.getPlayerHand(selectedPlayer).remove(selectedCard);

          // Deselect the card and player
          selectedCard = null;
          selectedPlayer = null;

          // Reset the border of the previously selected card panel
          if (previouslySelectedCardPanel != null) {
            previouslySelectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            previouslySelectedCardPanel = null;
          }

          // Update the GUI
          updateGridPanel();
          updateHandPanel(redHandPanel, gameModel.getRedPlayer());
          updateHandPanel(blueHandPanel, gameModel.getBluePlayer());
        } else {
          System.out.println("Model is not an instance of ThreeTriosModel.");
        }
      } else {
        System.out.println("Invalid cell selected. Cannot place card on a hole.");
      }
    }
  }

  private void updateGridPanel() {
    gridPanel.removeAll();
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getColumns();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        JPanel cellPanel = new JPanel();
        cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cellPanel.setBackground(model.getGrid().getCell(row, col).isHole() ? Color.YELLOW : Color.GRAY);

        CardCell cell = (CardCell) model.getGrid().getCell(row, col);
        if (cell != null && cell.getCard() != null) {
          JLabel cardLabel = new JLabel(cell.getCard().getName());
          cellPanel.add(cardLabel);
        }

        int finalRow = row;
        int finalCol = col;

        cellPanel.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            handleGridClick(finalRow, finalCol);
          }
        });

        gridPanel.add(cellPanel);
      }
    }

    gridPanel.revalidate();
    gridPanel.repaint();
  }

  private void updateHandPanel(JPanel handPanel, Player player) {
    handPanel.removeAll();
    List<Card> hand = model.getPlayerHand(player);
    Color backgroundColor = player.equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN;

    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);

      CardPanel cardPanel = new CardPanel(
              card.getAttackValue(Direction.NORTH),
              card.getAttackValue(Direction.SOUTH),
              card.getAttackValue(Direction.EAST),
              card.getAttackValue(Direction.WEST),
              backgroundColor);

      int index = i;
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(player, index);
        }
      });

      handPanel.add(cardPanel);
    }

    handPanel.revalidate();
    handPanel.repaint();
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
          handleCardClick(player, index);
        }
      });

      handPanel.add(cardPanel);
    }

    return handPanel;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(800, 600); // Default window size
  }

  @Override
  public void renderGrid(Grid grid) {

  }

  @Override
  public void renderPlayerHand(Player player, List<Card> hand) {

  }
}
