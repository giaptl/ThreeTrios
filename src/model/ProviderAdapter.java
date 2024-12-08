package model;

import java.awt.event.ActionListener;

import

import javax.swing.*;

import finalProviderCode.view.ThreeTriosView;


public class ProviderAdapter implements ThreeTriosView {
  private ThreeTriosView adaptee;

  public ThreeTriosViewAdapter(ThreeTriosView adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public JPanel getLeftPanel() {
    return adaptee.getLeftPanel();
  }

  @Override
  public JPanel getRightPanel() {
    return adaptee.getRightPanel();
  }

  @Override
  public JPanel getGridPanel() {
    return adaptee.getGridPanel();
  }

  @Override
  public ActionListener createCellClickListener(int row, int col, JButton testButton) {
    return adaptee.createCellClickListener(row, col, testButton);
  }

  @Override
  public void highlightedCard() {
    adaptee.highlightedCard();
  }

  @Override
  public void displayMessage(String message) {
    adaptee.displayMessage(message);
  }

  @Override
  public void updateTurnIndicator(String playerName) {
    adaptee.updateTurnIndicator(playerName);
  }

}
