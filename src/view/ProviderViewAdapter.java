package view;

import java.awt.event.ActionListener;

import javax.swing.*;

import controller.PlayerActionListener;
import model.ICard;
import model.ThreeTriosModel;

/**
 * Adapter class to make the provider's view compatible with our game.
 */

public class ProviderViewAdapter {
//  private final IGameView gameView;
//  private final ThreeTriosModel model;
//
//  public ProviderViewAdapter(IGameView gameView, ThreeTriosModel model) {
//    this.gameView = gameView;
//    this.model = model;
//  }
//
//  @Override
//  public JPanel getLeftPanel() {
//    // Assuming your GameView has a method to get the red player's panel
//    return gameView.getRedPlayerPanel();
//  }
//
//  @Override
//  public JPanel getRightPanel() {
//    // Assuming your GameView has a method to get the blue player's panel
//    return gameView.getBluePlayerPanel();
//  }
//
//  @Override
//  public JPanel getGridPanel() {
//    // Assuming your GameView has a method to get the grid panel
//    return gameView.getGridPanel();
//  }
//
//  @Override
//  public ActionListener createCellClickListener(int row, int col, JButton testButton) {
//    return e -> {
//      // Translate the cell click to your existing onGridCellSelected method
//      for (PlayerActionListener listener : gameView.getPlayerActionListeners()) {
//        listener.onGridCellSelected(row, col);
//      }
//    };
//  }
//
//  @Override
//  public void highlightedCard() {
//    // This method doesn't have a direct equivalent in your implementation
//    // You might need to add this functionality to your existing view
//  }
//
//  @Override
//  public void displayMessage(String message) {
//    gameView.showError(message);
//  }
//
//  @Override
//  public void updateTurnIndicator(String playerName) {
//    IPlayer currentPlayer = "RED".equals(playerName) ? model.getRedPlayer() : model.getBluePlayer();
//    // Assuming you have a method to update the current player in your view
//    gameView.updateCurrentPlayer(currentPlayer);
//  }
}
