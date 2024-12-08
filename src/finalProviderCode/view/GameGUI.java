package finalProviderCode.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import finalProviderCode.controller.PlayerAction;
import finalProviderCode.model.CellInterfaceProvider;
import finalProviderCode.model.GridProvider;
import finalProviderCode.model.PlayerProvider;
import finalProviderCode.model.ThreeTriosModelProvider;
import finalProviderCode.model.CardProvider;

/**
 * The {@code GameGUI} class manages the main user interface for the Three Trios game.
 */
public class GameGUI extends JFrame implements ThreeTriosView {
  private final ThreeTriosModelProvider model;
  private final JPanel gridPanel;
  private final SidePanel leftPanel;
  private final SidePanel rightPanel;
  private CardProvider selectedCard;
  private PlayerAction playerActionListener;
  private JButton[][] gridButtons;

  /**
   * Constructs a {@code GameGUI} object that sets up the user interface for the game.
   *
   * @param model the game model used to manage game state and interactions
   */
  public GameGUI(ThreeTriosModelProvider model) {
    this.model = model;
    setTitle("Three Trios Game");
    setSize(1200, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Initialize the side panels for the RED and BLUE players
    leftPanel = new SidePanel(model.getRedPlayer(), Color.PINK);
    rightPanel = new SidePanel(model.getBluePlayer(), Color.CYAN);

    // Initialize the grid panel
    gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(model.getGridRows(), model.getGridCols()));
    initializeGrid();

    // Add components to the frame
    add(leftPanel, BorderLayout.WEST);
    add(gridPanel, BorderLayout.CENTER);
    add(rightPanel, BorderLayout.EAST);

    setVisible(true);
  }

  /**
   * Initializes the game grid by adding buttons for each cell.
   * Each button represents a cell that can be occupied by a card.
   */
  private void initializeGrid() {
    gridButtons = new JButton[model.getGridRows()][model.getGridCols()]; // Initialize the array

    for (int r = 0; r < model.getGridRows(); r++) {
      for (int c = 0; c < model.getGridCols(); c++) {
        JButton cellButton = new JButton();
        cellButton.setOpaque(true);
        cellButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (model.isCellOccupied(r, c)) {
          String owner = (String) model.getCellOwner(r, c);
          cellButton.setText(model.getCardValueAt(r, c));

          if ("RED".equals(owner)) {
            cellButton.setBackground(Color.RED);
          } else if ("BLUE".equals(owner)) {
            cellButton.setBackground(Color.CYAN);
          } else {
            cellButton.setBackground(Color.LIGHT_GRAY);
          }

          cellButton.setEnabled(false); // Disable already occupied cells
        } else {
          cellButton.setBackground((r + c) % 2 == 0 ? Color.YELLOW : Color.GRAY);
          cellButton.addActionListener(new CellClickListener(r, c, cellButton));
        }

        gridButtons[r][c] = cellButton; // Store the button in the array
        gridPanel.add(cellButton);
      }
    }
  }


  /**
   * Returns the left panel containing the UI for the RED player.
   *
   * @return the left panel
   */
  public JPanel getLeftPanel() {
    return leftPanel;
  }

  /**
   * Returns the right panel containing the UI for the BLUE player.
   *
   * @return the right panel
   */
  public JPanel getRightPanel() {
    return rightPanel;
  }

  /**
   * Returns the grid panel representing the game board.
   *
   * @return the grid panel
   */
  public JPanel getGridPanel() {
    return gridPanel;
  }

  /**
   * Creates an ActionListener for handling cell click events.
   *
   * @param row        the row index of the cell
   * @param col        the column index of the cell
   * @param testButton the button associated with the cell
   * @return an ActionListener for cell click events
   */
  public ActionListener createCellClickListener(int row, int col, JButton testButton) {
    return new CellClickListener(row, col, testButton);
  }

  @Override
  public void highlightedCard() {
    // PROVIDER'S VIEW CLASS DID NOT IMPLEMENT THEIR VIEW INTERFACE. WE HAD TO MANUALLY IMPLEMENT
    // IT AND THIS REMAINING METHOD.
  }

  /**
   * listerner to highlight the card.
   *
   * @param card the card to be highlighted
   */
  public void highlightedCard(CardProvider card) {
    System.out.println("Highlighting card " + card);
  }

  /**
   * listener to show the message.
   *
   * @param message the message to be displayed
   */
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(null, message);
  }

  /**
   * listener to update the turn for the other player.
   *
   * @param playerName player's turn
   */
  public void updateTurnIndicator(String playerName) {
    System.out.println("It is now " + playerName + "'s turn.");
  }

  /**
   * update the grid.
   *
   * @param grid the grid to be updated.
   */
  public void updateGrid(GridProvider grid) {
    gridPanel.removeAll(); // Clear the existing grid display
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        CellInterfaceProvider cell = grid.getCell(row, col);

        // Create a final copy of row and col for the lambda
        final int currentRow = row;
        final int currentCol = col;

        JButton cellButton = new JButton(cell.render());
        cellButton.setOpaque(true);
        cellButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (cell.isEmpty()) {
          // Empty cell styling
          cellButton.setBackground((currentRow + currentCol) % 2 == 0 ? Color.YELLOW : Color.GRAY);
          cellButton.addActionListener(e -> {
            if (selectedCard != null) {
              playerActionListener.onCardPlaced(selectedCard, currentRow, currentCol);
            } else {
              JOptionPane.showMessageDialog(gridPanel,
                      "No card selected. Please select a card first.");
            }
          });
        } else {
          // Filled cell styling
          String owner = cell.getOwner();
          cellButton.setBackground("RED".equals(owner) ? Color.RED : Color.CYAN);
          cellButton.setEnabled(false);
        }

        gridPanel.add(cellButton);
      }
    }

    gridPanel.revalidate();
    gridPanel.repaint();
  }


  /**
   * A private class that handles click events for the cells on the grid.
   */
  private class CellClickListener implements ActionListener {
    private final int row;
    private final int col;
    private final JButton cellButton;

    /**
     * Constructs a {@code CellClickListener} for a specific cell.
     *
     * @param row        the row index of the cell
     * @param col        the column index of the cell
     * @param cellButton the button associated with the cell
     */
    public CellClickListener(int row, int col, JButton cellButton) {
      this.row = row;
      this.col = col;
      this.cellButton = cellButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      PlayerProvider currentPlayer = model.getCurrentPlayer();
      CardProvider card = model.getNextCardForPlayer(currentPlayer);

      if (card != null && model.placeCard(currentPlayer, card, row, col)) {
        cellButton.setText(card.toString());
        cellButton.setBackground(currentPlayer.getColor().equals("RED") ? Color.RED : Color.BLUE);
        cellButton.setEnabled(false);
        refreshGrid();
        if (model.isGameOver()) {
          JOptionPane.showMessageDialog(GameGUI.this, "Game Over! Winner: "
                  + model.getWinner().getName());
        }
      } else {
        JOptionPane.showMessageDialog(GameGUI.this, "Invalid move. Try again.");
      }
    }
  }

  /**
   * Refreshes the grid by reinitializing and repainting it.
   */
  private void refreshGrid() {
    gridPanel.removeAll();
    initializeGrid();
    gridPanel.revalidate();
    gridPanel.repaint();
  }


  /**
   * set the listener for player's actions.
   *
   * @param listener the listener
   */
  public void setPlayerActionListener(PlayerAction listener) {
    this.playerActionListener = listener;

    for (int row = 0; row < model.getGridRows(); row++) {
      for (int col = 0; col < model.getGridCols(); col++) {
        JButton cellButton = getGridButton(row, col);
        if (cellButton != null) {
          int currentRow = row;
          int currentCol = col;

          cellButton.addActionListener(e -> {
            if (selectedCard != null) {
              listener.onCardPlaced(selectedCard, currentRow, currentCol);
            } else {
              JOptionPane.showMessageDialog(cellButton,
                      "No card selected. Please select a card first.");
            }
          });
        }
      }
    }
  }

  private JButton getGridButton(int row, int col) {
    if (row < 0 || row >= gridButtons.length || col < 0 || col >= gridButtons[0].length) {
      throw new IllegalArgumentException("Invalid grid position: (" + row + ", " + col + ")");
    }
    return gridButtons[row][col];
  }


}
