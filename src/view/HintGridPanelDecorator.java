package view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ICard;
import model.ReadOnlyThreeTriosModel;
import player.IPlayer;

/**
 * Represents a decorator for a JPanel in a grid panel that displays the number of cards that can
 * be flipped by selecting a card at that cell.
 */
public class HintGridPanelDecorator implements GridPanelDecorator {
  private final ReadOnlyThreeTriosModel model;
  private final IPlayer currentPlayer;
  private final ICard selectedCard;

  /**
   * Constructs a HintGridPanelDecorator.
   * @param model the read-only model of the game
   * @param currentPlayer the current player
   * @param selectedCard the selected card
   */
  public HintGridPanelDecorator(ReadOnlyThreeTriosModel model, IPlayer currentPlayer,
                                ICard selectedCard) {
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