package model;

import java.util.List;

public class Player {

  List<Card> hand;
  String name;


  public Player(String playerName, List<Card> hand) {
    this.name = playerName;
    this.hand = hand;
  }

  public String getName() {
    return name;
  }

  public List<Card> getHand() {
    return List.copyOf(hand);
  }
}
