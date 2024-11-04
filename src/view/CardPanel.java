package view;

import java.awt.*;

import javax.swing.JPanel;

public class CardPanel extends JPanel {
  private final int topValue;
  private final int leftValue;
  private final int rightValue;
  private final int bottomValue;

  public CardPanel(int topValue, int leftValue, int rightValue, int bottomValue) {
    this.topValue = topValue;
    this.leftValue = leftValue;
    this.rightValue = rightValue;
    this.bottomValue = bottomValue;

    // Set preferred size for each card
    setPreferredSize(new Dimension(100, 150));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Draw card background
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, getWidth(), getHeight());

    // Draw border around the card
    g2d.setColor(Color.BLACK);
    g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

    // Set font for drawing numbers
    g2d.setFont(new Font("Arial", Font.BOLD, 20));
    FontMetrics fm = g.getFontMetrics();

    // Draw top value
    String topText = String.valueOf(topValue);
    int topX = (getWidth() - fm.stringWidth(topText)) / 2;
    g2d.drawString(topText, topX, fm.getHeight());

    // Draw left value
    String leftText = String.valueOf(leftValue);
    int leftY = (getHeight() + fm.getAscent()) / 2;
    g2d.drawString(leftText, fm.getAscent(), leftY);

    // Draw right value
    String rightText = String.valueOf(rightValue);
    int rightX = getWidth() - fm.stringWidth(rightText) - fm.getAscent();
    g2d.drawString(rightText, rightX, leftY);

    // Draw bottom value
    String bottomText = String.valueOf(bottomValue);
    int bottomX = (getWidth() - fm.stringWidth(bottomText)) / 2;
    g2d.drawString(bottomText, bottomX, getHeight() - fm.getDescent());
  }
}
