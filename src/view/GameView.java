package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.Controller;
import model.Card;
import model.Player;
import model.ReadOnlyThreeTriosModel;

public class GameView extends JFrame implements IGameView {
  private final ReadOnlyThreeTriosModel model;
  private Controller controller;
  private JPanel gridPanel;
  private JPanel redHandPanel;
  private JPanel blueHandPanel;
  private JPanel previouslySelectedCardPanel = null;
  private final GridPanelManager gridPanelManager;
  private final HandPanelManager handPanelManager;

  public GameView(ReadOnlyThreeTriosModel model) {
    this.model = model;
    this.gridPanelManager = new GridPanelManager(model);
    this.handPanelManager = new HandPanelManager(model);
    setTitle("ThreeTrios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    redHandPanel = handPanelManager.createHandPanel(model.getRedPlayer());
    blueHandPanel = handPanelManager.createHandPanel(model.getBluePlayer());
    gridPanel = gridPanelManager.getGridPanel();

    add(redHandPanel, BorderLayout.WEST);
    add(blueHandPanel, BorderLayout.EAST);
    add(gridPanel, BorderLayout.CENTER);

    pack();
    setVisible(true);
  }

  public void setController(Controller controller) {
    this.controller = controller;
    gridPanelManager.setController(controller);
    handPanelManager.setController(controller);
  }

  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void updateCardSelection(Player player, Card card) {
    if (previouslySelectedCardPanel != null) {
      previouslySelectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      previouslySelectedCardPanel = null;
    }

    if (player != null && card != null) {
      JPanel cardPanel;
      int cardIndex = model.getPlayerHand(player).indexOf(card);

      if (player.equals(model.getRedPlayer())) {
        cardPanel = (JPanel) redHandPanel.getComponent(cardIndex);
      } else {
        cardPanel = (JPanel) blueHandPanel.getComponent(cardIndex);
      }

      cardPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
      previouslySelectedCardPanel = cardPanel;
    }
  }

  @Override
  public void updateGridCell(int row, int col, Card card) {
    gridPanelManager.updateGridPanel();
  }

  @Override
  public void removeCardFromHandPanel(Player player, Card card) {
    JPanel handPanel = player.equals(model.getRedPlayer()) ? redHandPanel : blueHandPanel;
    List<Card> hand = model.getPlayerHand(player);

    for (int i = 0; i < hand.size(); i++) {
      if (hand.get(i).equals(card)) {
        handPanel.remove(i);
        break;
      }
    }
    handPanel.revalidate();
    handPanel.repaint();
  }

  @Override
  public void refreshView() {
    remove(gridPanel);
    gridPanelManager.updateGridPanel();
    handPanelManager.updateHandPanel(redHandPanel, model.getRedPlayer());
    handPanelManager.updateHandPanel(blueHandPanel, model.getBluePlayer());
    add(gridPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(800, 600);
  }
}