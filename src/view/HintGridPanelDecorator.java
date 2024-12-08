package view;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import finalProviderCode.model.ReadOnlyThreeTriosModelProvider;
import model.ICard;
import model.ReadOnlyThreeTriosModel;
import player.IPlayer;

public class HintGridPanelDecorator implements GridPanelDecorator {
  private final ReadOnlyThreeTriosModel model;
  private final IPlayer currentPlayer;
  private final ICard selectedCard;

  public HintGridPanelDecorator(ReadOnlyThreeTriosModel model, IPlayer currentPlayer, ICard selectedCard) {
    this.model = model;
    this.currentPlayer = currentPlayer;
    this.selectedCard = selectedCard;
  }

  @Override
  public void decorate(JPanel cellPanel, int row, int col) {
    if (selectedCard != null && !model.getGrid().getCell(row, col).isHole()) {
      int numCardsAbleToFlip = model.getNumCardsAbleToFlip(currentPlayer, selectedCard, row, col);
      JLabel hintLabel = new JLabel(String.valueOf(numCardsAbleToFlip));
      hintLabel.setHorizontalAlignment(JLabel.RIGHT);
      cellPanel.setLayout(new BorderLayout());
      cellPanel.add(hintLabel, BorderLayout.SOUTH);
    }
  }
}