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
import controller.ControllerFeatures;
import model.Card;
import model.Player;
import model.ReadOnlyThreeTriosModel;

/**
 * Represents the main game view for the ThreeTrios game, including the grid and player hands.
 * This class is responsible for initializing and managing the main components of the game view,
 * such as the grid panel and the hand panels for both players. It also handles user interactions
 * and updates the view based on the game state.
 *
 * <p>The GameView class extends JFrame and implements the IGameView interface, providing methods
 * to set the controller, show error messages, update card selection, update grid cells, remove
 * cards from hand panels, and refresh the entire view.</p>
 *
 * <p>The main components of the GameView include:</p>
 * <ul>
 *   <li>GridPanelManager: Manages the creation and updating of the grid panel.</li>
 *   <li>JPanel gridPanel: The panel representing the game grid.</li>
 *   <li>JPanel redHandPanel: The panel representing the red player's hand.</li>
 *   <li>JPanel blueHandPanel: The panel representing the blue player's hand.</li>
 * </ul>
 *
 * <p>The GameView class also maintains a reference to the previously selected card panel to
 * highlight the currently selected card.</p>
 */
public class GameView extends JFrame implements IGameView {
  private final ReadOnlyThreeTriosModel model;
  private JPanel gridPanel;
  private JPanel redHandPanel;
  private JPanel blueHandPanel;
  private JPanel previouslySelectedCardPanel = null;
  private final GridPanelManager gridPanelManager;
  private final HandPanelManager handPanelManager;
  private ControllerFeatures features;

  /**
   * Constructs a GameView with the specified model.
   *
   * @param model the read-only model of the game
   */
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

  /**
   * Sets the controller for the manager classes.
   */
  public void setController(Controller controller) {
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