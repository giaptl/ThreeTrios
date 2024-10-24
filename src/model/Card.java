package model;

import java.util.HashMap;
import java.util.Map;

public class Card {

  private final String name;
  private Map<Direction, Integer> attackValues;

  public Card(String name, int north, int south, int east, int west) {
    this.name = name;
    this.attackValues = new HashMap<>();
    this.attackValues.put(Direction.NORTH, north);
    this.attackValues.put(Direction.SOUTH, south);
    this.attackValues.put(Direction.EAST, east);
    this.attackValues.put(Direction.WEST, west);
  }

  public String getName() {
    return name;
  }

  public int getAttackValue(Direction direction) {
    return attackValues.getOrDefault(direction, 0);
  }
}
