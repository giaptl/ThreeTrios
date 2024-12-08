package finalProviderCode.model;

/**
 * Represents a single cell on the game grid, which may contain a card owned by a player.
 * A cell can either be empty, filled with a card belonging to a specific owner, or be a hole.
 */
public interface Cell {

  /**
   * Checks if the cell is empty (i.e., no card has been placed and it's not a hole).
   *
   * @return {@code true} if the cell is empty, {@code false} otherwise
   */
  public boolean isEmpty();

  /**
   * Sets a card into this cell and assigns an owner to the card.
   *
   * @param card  the card to be placed in the cell
   * @param owner the owner of the card, represented as a string (e.g., "RED" or "BLUE")
   * @throws IllegalStateException if the cell is a hole
   */
  public void setCard(Card card, String owner);

  /**
   * Clears the cell, removing the card and its owner.
   */
  public void clearCell();

  /**
   * Retrieves the card placed in this cell.
   *
   * @return the {@link Card} in this cell, or {@code null} if the cell is empty
   */
  public Card getCard();

  /**
   * Checks if the cell is a hole or a playable card cell.
   *
   * @return {@code true} if the cell is a hole, {@code false} otherwise
   */
  public boolean isHole();

  /**
   * Retrieves the owner of the card in this cell.
   *
   * @return the owner of the card, represented as a string
   * (e.g., "RED" or "BLUE"), or {@code null} if no card is present
   */
  public String getOwner();
  

  /**
   * Renders a string representation of the cell for display purposes.
   * If the cell is a hole, it returns "X".
   * If the cell is empty, it returns an underscore ("_").
   * If it contains a card, it returns the first character of the owner's name.
   *
   * @return a string representing the content of the cell
   */
  public String render();

  /**
   * Checks if the cell is occupied (i.e., contains a card).
   *
   * @return {@code true} if the cell contains a card, {@code false} otherwise
   */
  public boolean isOccupied();
}


