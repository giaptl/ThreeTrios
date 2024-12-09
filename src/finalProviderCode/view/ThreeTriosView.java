package finalProviderCode.view;

import java.awt.event.ActionListener;

import javax.swing.*;

import finalProviderCode.model.GridProvider;

public interface ThreeTriosView {

  /**
   * Returns the left panel containing the UI for the RED player.
   *
   * @return the left panel
   */
  public JPanel getLeftPanel();

  /**
   * Returns the right panel containing the UI for the BLUE player.
   *
   * @return the right panel
   */
  public JPanel getRightPanel();

  /**
   * Returns the grid panel representing the game board.
   *
   * @return the grid panel
   */
  public JPanel getGridPanel();

  /**
   * Creates an ActionListener for handling cell click events.
   *
   * @param row        the row index of the cell
   * @param col        the column index of the cell
   * @param testButton the button associated with the cell
   * @return an ActionListener for cell click events
   */
  public ActionListener createCellClickListener(int row, int col, JButton testButton);

  /**
   * listerner to highlight the card.
   *
   * @param card the card to be highlighted
   */
  public void highlightedCard();

  /**
   * listener to show the message.
   *
   * @param message the message to be displayed
   */
  public void displayMessage(String message);

  /**
   * listener to update the turn for the other player.
   *
   * @param playerName player's turn
   */
  public void updateTurnIndicator(String playerName);

  /**
   * These methods were added by US not the provider to make the view compatible.
   * The provider's were missing a lot of features and we had to add this in order to make
   * their view even work since they didn't even have a setVisible in their view.
   *
   * @param title filler text.
   */
  void setTitle(String title);

  /**
   * These methods were added by US not the provider to make the view compatible.
   * The provider's were missing a lot of features and we had to add this in order to make
   * their view even work since they didn't even have a setVisible in their view.
   *
   * @param visible filler text.
   */
  void setVisible(boolean visible);


}
