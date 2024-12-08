package finalProviderCode.model;

/**
 * Represents a filled cell on the game grid.
 * A filled cell contains a {@link CardProvider} owned by a player.
 */
public class FilledCellProvider extends CellProvider {

  /** The owner of the card in this cell, represented as a string (e.g., "RED" or "BLUE"). */
  private final String owner;

  /**
   * Constructs a new {@link FilledCellProvider} with the specified card and owner.
   *
   * @param card  the card placed in the cell
   * @param owner the owner of the card, represented as a string
   */
  public FilledCellProvider(CardProvider card, String owner) {
    setCard(card, owner);
    this.owner = owner;
  }

  /**
   * Overrides the {@link CellProvider#isEmpty()} method to always return {@code false}.
   *
   * @return {@code false}, indicating that this cell is not empty
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Overrides the {@link CellProvider#render()} method to return the symbol representing
   * the owner of the card in this cell.
   *
   * @return the first character of the owner's string representation
   */
  @Override
  public String render() {
    return owner.charAt(0) + "";
  }
}
