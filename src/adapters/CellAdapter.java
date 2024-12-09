package adapters;

import finalProviderCode.model.CardProvider;
import finalProviderCode.model.CellInterfaceProvider;
import model.Cell;
import model.ICard;

public class CellAdapter implements CellInterfaceProvider {
  private final Cell cell;

  public CellAdapter(Cell cell) {
    this.cell = cell;
  }

  @Override
  public boolean isEmpty() {
    return cell.isEmpty();
  }

  @Override
  public void setCard(CardProvider card, String owner) {

  }

  @Override
  public void clearCell() {

  }

  @Override
  public CardProvider getCard() {
    return null;
  }

  @Override
  public boolean isHole() {
    return false;
  }

  @Override
  public String getOwner() {
    return cell.getOwner().toString();
  }

  @Override
  public String render() {
    ICard card = cell.getCard();
    return card != null ? card.toString() : "";
  }

  @Override
  public boolean isOccupied() {
    return false;
  }
}