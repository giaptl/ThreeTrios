package model;

import java.util.List;

public interface IGameModel {

  void startGame(Grid grid, List<Card> cards);

  Player getCurrentPlayer();

  Grid getGrid();

  List<Card> getPlayerHand(Player player);

  boolean isGameOver();

  Player getWinner();

  void playCard(Player player, Card card, int row, int col);

  void startBattlePhase(int row, int col);

}
