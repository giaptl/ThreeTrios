package model.grid;

import model.cards.Card;

/**
 * Represents a filled cell on the game grid.
 * A filled cell contains a {@link Card} owned by a player.
 */
public class FilledCell extends Cell {

  /** The owner of the card in this cell, represented as a string (e.g., "RED" or "BLUE"). */
  private final String owner;

  /**
   * Constructs a new {@link FilledCell} with the specified card and owner.
   *
   * @param card  the card placed in the cell
   * @param owner the owner of the card, represented as a string
   */
  public FilledCell(Card card, String owner) {
    setCard(card, owner);
    this.owner = owner;
  }

  /**
   * Overrides the {@link Cell#isEmpty()} method to always return {@code false}.
   *
   * @return {@code false}, indicating that this cell is not empty
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Overrides the {@link Cell#render()} method to return the symbol representing
   * the owner of the card in this cell.
   *
   * @return the first character of the owner's string representation
   */
  @Override
  public String render() {
    return owner.charAt(0) + "";
  }
}
