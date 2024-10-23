package model;

public interface Cell {

  private boolean isHole(){
    return false;
  }

  private boolean isEmpty() {
    return false;
  }

  private boolean isOccupied() {
    return false;
  }

  private Card getCard() {
    return null;
  }

  private Player getOwner() {
    return null;
  }

}
