package view;

import java.util.List;

import model.Card;
import model.Grid;
import model.Player;

public interface IGameView {

  void renderGrid(Grid grid);

  void renderPlayerHand(Player player, List<Card> hand);

}
