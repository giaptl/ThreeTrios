package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;
import controller.Controller;
import model.Card;
import model.Player;
import model.ReadOnlyThreeTriosModel;

public class HandPanelManager {
  private final ReadOnlyThreeTriosModel model;
  private Controller controller;

  public HandPanelManager(ReadOnlyThreeTriosModel model) {
    this.model = model;
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public JPanel createHandPanel(Player player) {
    JPanel handPanel = new JPanel();
    handPanel.setLayout(new GridLayout(player.getHand().size(), 1));
    Color backgroundColor = player.equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN;

    List<Card> hand = model.getPlayerHand(player);
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      CardPanel cardPanel = CardPanelFactory.createCardPanel(card, backgroundColor);

      int index = i;
      cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
          controller.handleCardClick(player, index);
        }
      });

      handPanel.add(cardPanel);
    }

    return handPanel;
  }

  public void updateHandPanel(JPanel handPanel, Player player) {
    handPanel.removeAll();
    List<Card> hand = model.getPlayerHand(player);
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      CardPanel cardPanel = CardPanelFactory.createCardPanel(
              card,
              player.equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN
      );
      int index = i;
      cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
          controller.handleCardClick(player, index);
        }
      });
      handPanel.add(cardPanel);
    }
    handPanel.revalidate();
    handPanel.repaint();
  }
}