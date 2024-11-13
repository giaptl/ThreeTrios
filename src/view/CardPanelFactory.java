package view;

import java.awt.Color;
import model.Card;
import model.Direction;

/**
 * Factory class for creating CardPanel instances.
 */
public class CardPanelFactory {

  /**
   * Creates a CardPanel for the given card with the specified background color.
   *
   * @param card the card for which the panel is to be created
   * @param backgroundColor the background color of the card panel
   * @return a new CardPanel instance
   */
  public static CardPanel createCardPanel(Card card, Color backgroundColor) {
    return new CardPanel(
            card.getAttackValue(Direction.NORTH),
            card.getAttackValue(Direction.SOUTH),
            card.getAttackValue(Direction.EAST),
            card.getAttackValue(Direction.WEST),
            backgroundColor
    );
  }
}