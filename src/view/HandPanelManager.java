package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;
import controller.PlayerActionListener;
import model.Card;
import model.ICard;
import player.IPlayer;
import model.ReadOnlyThreeTriosModel;

/**
 * Manages the creation and updating of the hand panels in the ThreeTrios game.
 */
public class HandPanelManager {
  private final ReadOnlyThreeTriosModel model;
  private final List<PlayerActionListener> playerActionListeners;

  /**
   * Constructs a HandPanelManager with the specified model and listeners.
   *
   * @param model the read-only model of the game
   * @param playerActionListeners the list of player action listeners
   */
  public HandPanelManager(ReadOnlyThreeTriosModel model,
                          List<PlayerActionListener> playerActionListeners) {
    this.model = model;
    this.playerActionListeners = playerActionListeners;
  }

  /**
   * Creates a hand panel for the specified player.
   *
   * @param player the player whose hand panel is to be created
   * @return the created hand panel
   */
  public JPanel createHandPanel(IPlayer player) {
    JPanel handPanel = new JPanel();
    handPanel.setLayout(new GridLayout(player.getHand().size(), 1));
    Color backgroundColor = player.equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN;

    List<ICard> hand = model.getPlayerHand(player);
    for (int i = 0; i < hand.size(); i++) {
      ICard card = hand.get(i);
      CardPanel cardPanel = CardPanelFactory.createCardPanel(card, backgroundColor);

      int index = i;
      cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
          for (PlayerActionListener listener : playerActionListeners) {
            listener.onCardSelected(player, index);
          }
        }
      });

      handPanel.add(cardPanel);
    }

    return handPanel;
  }

  /**
   * Updates the hand panel for the specified player.
   *
   * @param handPanel the hand panel to be updated
   * @param player the player whose hand panel is to be updated
   */
  public void updateHandPanel(JPanel handPanel, IPlayer player) {
    handPanel.removeAll();
    List<ICard> hand = model.getPlayerHand(player);
    for (int i = 0; i < hand.size(); i++) {
      ICard card = hand.get(i);
      CardPanel cardPanel = CardPanelFactory.createCardPanel(
              card,
              player.equals(model.getRedPlayer()) ? Color.PINK : Color.CYAN
      );
      int index = i;
      cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
          for (PlayerActionListener listener : playerActionListeners) {
            listener.onCardSelected(player, index);
          }
        }
      });
      handPanel.add(cardPanel);
    }
    handPanel.revalidate();
    handPanel.repaint();
  }
}