package model;

public interface Cell {

  boolean isHole();

  boolean isEmpty();

  boolean isOccupied();

  Card getCard();

  Player getOwner();

}
