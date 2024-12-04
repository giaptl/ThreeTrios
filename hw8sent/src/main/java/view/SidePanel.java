package view;

import model.cards.Card;
import players.Player;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The {@code SidePanel} class is responsible for rendering the side panel
 * displaying a player's card details in the game.
 */
public class SidePanel extends JPanel {

  private JPanel selectedCardPanel = null; // Track the currently selected card panel

  /**
   * Constructs a {@code SidePanel} for the given player and initializes it with
   * the player's cards.
   *
   * @param player the {@code Player} whose cards will be displayed
   * @param backgroundColor the {@code Color} of the panel background
   */
  public SidePanel(Player player, Color backgroundColor) {
    setBackground(backgroundColor);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertical alignment for cards

    List<Card> hand = player.getHand();

    // Iterate through each card in the player's hand
    for (Card card : hand) {
      JPanel cardPanel = createCardPanel(card);
      cardPanel.setBackground(backgroundColor);
      add(cardPanel);
      add(Box.createVerticalStrut(10)); // Add spacing between cards
    }

    revalidate();
    repaint();
  }

  /**
   * Creates a {@code JPanel} for displaying a single card's values.
   *
   * @param card the {@code Card} to be displayed
   * @return a {@code JPanel} containing the card's details
   */
  private JPanel createCardPanel(Card card) {
    JPanel cardPanel = new JPanel(new GridLayout(3, 3));
    cardPanel.setPreferredSize(new Dimension(100, 100));
    cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Create labels for each card attribute
    JLabel northLabel = new JLabel(String.valueOf(card.getNorth()), SwingConstants.CENTER);
    JLabel southLabel = new JLabel(String.valueOf(card.getSouth()), SwingConstants.CENTER);
    JLabel eastLabel = new JLabel(String.valueOf(card.getEast()), SwingConstants.CENTER);
    JLabel westLabel = new JLabel(String.valueOf(card.getWest()), SwingConstants.CENTER);

    // Add the labels to the appropriate locations in the GridLayout
    cardPanel.add(new JLabel("")); // Top-left empty space
    cardPanel.add(northLabel); // Top-center for north value
    cardPanel.add(new JLabel("")); // Top-right empty space
    cardPanel.add(westLabel); // Center-left for west value
    cardPanel.add(new JLabel("")); // Center empty space
    cardPanel.add(eastLabel); // Center-right for east value
    cardPanel.add(new JLabel("")); // Bottom-left empty space
    cardPanel.add(southLabel); // Bottom-center for south value
    cardPanel.add(new JLabel("")); // Bottom-right empty space

    // Add a mouse listener to highlight the border when the card is clicked
    cardPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (selectedCardPanel != null) {
          // Reset the previously selected card's border
          selectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        // Highlight the clicked card's border
        selectedCardPanel = cardPanel;
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
      }
    });

    return cardPanel;
  }
}
