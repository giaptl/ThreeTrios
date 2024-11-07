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


import model.Card;
import model.Direction;
import model.Grid;
import model.Player;
import model.ReadOnlyThreeTriosModel;

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
                .getCell(row, col).isHole() ? Color.GRAY : Color.YELLOW);

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
  }

  /**
   * Handles clicking on a grid cell.
   */
//  private void handleGridClick(int row, int col) {
//    System.out.println("Clicked on grid cell at (" + row + ", " + col + ")");
//
//    if (selectedCard != null && selectedPlayer != null) {
//      // Logic to place card on grid can go here in future controller implementation
//      System.out.println("Attempting to place " + selectedCard.getName() + " from "
//              + selectedPlayer.getName() + " at (" + row + ", " + col + ")");
//
//      // Deselect after placing
//      selectedCard = null;
//      selectedPlayer = null;
//
//      // Optionally update UI to reflect changes in the grid
//    }
//  }

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

  private void handleGridClick(int row, int col) {
    System.out.println("Clicked on grid cell at (" + row + ", " + col + ")");

    if (selectedCard != null && selectedPlayer != null) {
      // Update the grid visually
      JPanel cellPanel = (JPanel) gridPanel.getComponent(row * 3 + col);
      cellPanel.removeAll();
      cellPanel.add(createCardLabel(selectedCard));
      cellPanel.revalidate();
      cellPanel.repaint();

      System.out.println("Placed " + selectedCard.getName() + " from "
              + selectedPlayer.getName() + " at (" + row + ", " + col + ")");

      // Remove the card from the player's hand visually
      JPanel handPanel = selectedPlayer == model.getRedPlayer() ? redHandPanel : blueHandPanel;
      handPanel.remove(previouslySelectedCardPanel);
      handPanel.revalidate();
      handPanel.repaint();

      // Deselect after placing
      selectedCard = null;
      selectedPlayer = null;
      previouslySelectedCardPanel = null;

      // Switch the current player (visually only)
      Player currentPlayer = model.getCurrentPlayer();
      Player nextPlayer = currentPlayer == model.getRedPlayer() ? model.getBluePlayer() : model.getRedPlayer();
      // You would need to implement setCurrentPlayer in your model for this to work
//       model.setCurrentPlayer(nextPlayer);

      // Update the UI to reflect changes
      updatePlayerHandsVisibility();
    }
  }

  private JLabel createCardLabel(Card card) {
    JLabel label = new JLabel(card.getName());
    label.setPreferredSize(new Dimension(60, 80));
    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    label.setHorizontalAlignment(JLabel.CENTER);
    return label;
  }

  private void updatePlayerHandsVisibility() {
    Player currentPlayer = model.getCurrentPlayer();
    redHandPanel.setVisible(currentPlayer == model.getRedPlayer());
    blueHandPanel.setVisible(currentPlayer == model.getBluePlayer());
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