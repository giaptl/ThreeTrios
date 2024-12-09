package adapters;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controller.Controller;
import controller.PlayerActionListener;
import finalProviderCode.model.GridProvider;
import finalProviderCode.view.ThreeTriosView;
import model.Grid;
import model.ICard;
import model.ReadOnlyThreeTriosModel;
import player.IPlayer;
import view.IGameView;

/**
 * Adapter class to make the provider's view compatible with our game.
 */
public class ViewAdapter implements IGameView {
  private final ThreeTriosView providerView;
  private final List<PlayerActionListener> listeners;
  private final ReadOnlyThreeTriosModel model;


  public ViewAdapter(ThreeTriosView providerView, ReadOnlyThreeTriosModel model) {
    if (providerView == null) {
      throw new IllegalArgumentException("Provider view cannot be null");
    }
    this.providerView = providerView;
    this.model = model;
    this.listeners = new ArrayList<>();
  }

  public ThreeTriosView getProviderView() {
    return providerView;
  }

  @Override
  public void showError(String message) {
    providerView.displayMessage(message);
  }

  @Override
  public void updateCardSelection(IPlayer player, ICard card) {
    providerView.highlightedCard();
  }

  @Override
  public void updateGridCell(int row, int col, ICard card) {
    providerView.getGridPanel().repaint();
    providerView.getLeftPanel().repaint();
    providerView.getRightPanel().repaint();
  }

  @Override
  public void removeCardFromHandPanel(IPlayer player, ICard card) {
    if (player.equals(model.getRedPlayer())) {
      providerView.getLeftPanel().repaint();
    } else {
      providerView.getRightPanel().repaint();
    }
  }

  @Override
  public void refreshView() {
    providerView.getGridPanel().repaint();
    providerView.getLeftPanel().repaint();
    providerView.getRightPanel().repaint();
  }

  @Override
  public void setController(Controller controller) {
    // WE ARE NOT ABLE TO IMPLEMENT THIS FEATURE BC THE PROVIDER'S VIEW DOES NOT TAKE IN THE
    // CONTROLLER
  }

  @Override
  public void showGameOver(IPlayer winner, int score) {
    String message = winner == null ?
            "Game Over! It's a tie! Score: " + score :
            "Game Over! Winner: " + winner.getName() + " Score: " + score;
    providerView.displayMessage(message);
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    listeners.add(listener);
    JButton[][] buttons = new JButton[3][3];  // Assuming 3x3 grid
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        JButton button = new JButton();
        buttons[row][col] = button;
        ActionListener cellListener = providerView.createCellClickListener(row, col, button);
        button.addActionListener(cellListener);
        int finalRow = row;
        int finalCol = col;
        button.addActionListener(e -> listener.onGridCellSelected(finalRow, finalCol));
      }
    }
  }

  @Override
  public void removePlayerActionListener(PlayerActionListener listener) {
    listeners.remove(listener);
  }
}