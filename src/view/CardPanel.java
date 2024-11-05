package view;

import java.awt.*;
import javax.swing.JPanel;

public class CardPanel extends JPanel {
  private final String northValue;
  private final String southValue;
  private final String eastValue;
  private final String westValue;
  private final Color backgroundColor;

  public CardPanel(String northValue, String southValue, String eastValue,
                   String westValue, Color backgroundColor) {
    this.northValue = northValue;
    this.southValue = southValue;
    this.eastValue = eastValue;
    this.westValue = westValue;
    this.backgroundColor = backgroundColor;

    // Set preferred size for each card
    setPreferredSize(new Dimension(100, 150));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Draw card background
    g2d.setColor(backgroundColor);
    g2d.fillRect(0, 0, getWidth(), getHeight());

    // Draw border around the card
    g2d.setColor(Color.BLACK);
    g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

    // Set font for drawing numbers
    g2d.setFont(new Font("Arial", Font.BOLD, 20));
    FontMetrics fm = g.getFontMetrics();

    // Draw north value
    int northX = (getWidth() - fm.stringWidth(northValue)) / 2;
    g2d.drawString(northValue, northX, fm.getHeight());

    // Draw south value
    int southX = (getWidth() - fm.stringWidth(southValue)) / 2;
    g2d.drawString(southValue, southX, getHeight() - fm.getDescent());

    // Draw east value
    int eastX = getWidth() - fm.stringWidth(eastValue) - fm.getAscent();
    int eastY = (getHeight() + fm.getAscent()) / 2;
    g2d.drawString(eastValue, eastX, eastY);

    // Draw west value
    int westY = (getHeight() + fm.getAscent()) / 2;
    g2d.drawString(westValue, fm.getAscent(), westY);
  }
}