package model;

import java.util.List;
import java.util.Map;

public class GameModel implements IGameModel {

  private Grid grid;
  private Player currentPlayer;
  private Map<Player, List<Card>> playerHands;

  public GameModel() {

  }

  @Override
  public void startGame(Grid grid, List<Card> cards) {

  }

  @Override
  public Player getCurrentPlayer() {
    return null;
  }

  @Override
  public Grid getGrid() {
    return null;
  }

  @Override
  public List<Card> getPlayerHand(Player player) {
    return List.of();
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Player getWinner() {
    return null;
  }

  @Override
  public void playCard(Player player, Card card, int row, int col) {

  }

  @Override
  public void startBattlePhase(int row, int col) {

  }
}
