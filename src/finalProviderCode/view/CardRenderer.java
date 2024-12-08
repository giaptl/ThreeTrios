package finalProviderCode.view;

import finalProviderCode.model.Card;
import finalProviderCode.model.Direction;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

/**
 * The {@code CardRenderer} class provides utility methods to draw and render
 * card values on the game grid.
 */
public class CardRenderer {

  /**
   * Draws the value of a card on the grid at the specified location
   * with the appropriate orientation.
   *
   * @param g2d       the {@code Graphics2D} object used for drawing
   * @param value     the value of the card to be drawn as a {@code String}
   * @param direction the {@code Direction} of the card value being drawn
   * @param centerX   the x-coordinate for the center of the card
   * @param centerY   the y-coordinate for the center of the card
   */
  public void drawCardValue(Graphics2D g2d, String value, Direction direction,
                            int centerX, int centerY) {
    FontMetrics fontMetrics = g2d.getFontMetrics();
    int x = centerX;
    int y = centerY;

    switch (direction) {
      case NORTH:
        y -= fontMetrics.getHeight();
        break;
      case SOUTH:
        y += fontMetrics.getDescent();
        break;
      case EAST:
        x += fontMetrics.stringWidth(value) / 2;
        break;
      case WEST:
        x -= fontMetrics.stringWidth(value) / 2;
        break;
      default:
        // No specific action needed for default; added for completeness
        break;
    }

    g2d.drawString(value, x, y);
  }

  /**
   * Draws all four values (NORTH, SOUTH, EAST, WEST) of the card at the specified location.
   *
   * @param g2d    the {@code Graphics2D} object used for drawing
   * @param card   the {@code Card} to be rendered
   * @param x      the x-coordinate of the top-left corner of the card
   * @param y      the y-coordinate of the top-left corner of the card
   * @param width  the width of the card
   * @param height the height of the card
   */
  public void drawCard(Graphics2D g2d, Card card, int x, int y, int width, int height) {
    g2d.setColor(java.awt.Color.BLACK);
    FontMetrics fontMetrics = g2d.getFontMetrics();
    int centerX = x + width / 2;
    int centerY = y + height / 2;

    // Draw each side's value at the appropriate position
    drawCardValue(
        g2d,
        String.valueOf(card.getNorth()),
        Direction.NORTH,
        centerX,
        y + fontMetrics.getHeight()
    );
    drawCardValue(
        g2d,
        String.valueOf(card.getSouth()),
        Direction.SOUTH,
        centerX,
        y + height - fontMetrics.getDescent()
    );
    drawCardValue(
        g2d,
        String.valueOf(card.getWest()),
        Direction.WEST,
        x + fontMetrics.stringWidth("X"),
        centerY
    );
    drawCardValue(
        g2d,
        String.valueOf(card.getEast()),
        Direction.EAST,
        x + width - fontMetrics.stringWidth("X"),
        centerY
    );
  }
}
