package model;

/**
 * Represents a cell on the grid that contains a card.
 */
public class CardCell implements Cell {

  private Card card;
  private Player owner;

  public CardCell() {
    this.card = null;
  }

  public CardCell(Card card, Player owner) {
    this.card = card;
    this.owner = owner;
  }

  @Override
  public boolean isHole() {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return card == null;
  }

  @Override
  public boolean isOccupied() {
    return card != null;
  }

  @Override
  public Card getCard() {
    return card;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  /**
   * Sets the card in the cell.
   * @param owner the owner to set for the CardCell
   */
  public void setOwner(Player owner) {
    this.owner = owner;
  }
}