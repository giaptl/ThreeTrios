package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.CardValues.A;

public class MockThreeTriosModel implements ThreeTriosModel {
  private Grid grid;
  private Player currentPlayer;
  private Player redPlayer;
  private Player bluePlayer;
  private boolean isGameOver;

  public MockThreeTriosModel() {
    this.grid = new Grid(3, 3);

    this.redPlayer = new Player("Red", new ArrayList<>());
    redPlayer.addCard(new Card("RedCard1", 1, 1, 1, 1));
    redPlayer.addCard(new Card("RedCard2", 3, 3, 3, 3));
    redPlayer.addCard(new Card("RedCard3", 4, 4, 4, 4));
    redPlayer.addCard(new Card("RedCard4", 6, 6, 6, 6));
    redPlayer.addCard(new Card("RedCard5", A.getValue(), A.getValue(), 1, 1));

    this.bluePlayer = new Player("Blue", new ArrayList<>());
    bluePlayer.addCard(new Card("BlueCard1", 2, 2, 2, 2));
    bluePlayer.addCard(new Card("BlueCard2", 5, 5, 5, 5));
    bluePlayer.addCard(new Card("BlueCard3", 7, 7, 7, 7));
    bluePlayer.addCard(new Card("BlueCard4", 9, 9, 9, 9));

    this.currentPlayer = redPlayer;
    this.isGameOver = false;

  }

  @Override
  public void startGameWithConfig(Grid grid, List<Card> cards, boolean shuffle) {
    // will not be used since we already assigned players cards in constructor
  }

  public void startGameWithConfig(Grid grid) {
    this.grid = grid;
  }

  @Override
  public void playCard(Player player, Card card, int row, int col) {
    if (!player.equals(currentPlayer)) {
      throw new IllegalArgumentException("It is not " + player.getName() + "'s turn.");
    }
    if (!player.getHand().contains(card)) {
      throw new IllegalArgumentException("Card is not in player's hand.");
    }
    if (!grid.getCell(row, col).isEmpty()) {
      throw new IllegalArgumentException("Cell is not empty.");
    }

    grid.setCell(row, col, new CardCell(card, player));
    player.removeCard(card);
    currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
  }

  @Override
  public void startBattlePhase(int row, int col) {
    // Implement if needed for testing
  }

  @Override
  public Grid getGrid() {
    return this.grid;
  }

  @Override
  public List<Card> getPlayerHand(Player player) {
    return player.getHand();
  }

  @Override
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  @Override
  public Player getOpponent(Player player) {
    return player.equals(redPlayer) ? bluePlayer : redPlayer;
  }

  @Override
  public int getNumCardsAbleToFlip(Player player, Card card, int row, int col) {
    // Simplified implementation for testing
    return 1;
  }

  @Override
  public boolean isGameOver() {
    return this.isGameOver;
  }

  @Override
  public Player getWinner() {
    // Simplified implementation for testing
    return isGameOver ? redPlayer : null;
  }

  @Override
  public Player getRedPlayer() {
    return this.redPlayer;
  }

  @Override
  public Player getBluePlayer() {
    return this.bluePlayer;
  }

  @Override
  public int getPlayerScore(Player player) {
    return getPlayerHand(player).size();
  }

  // Additional methods for testing
  public void setGameOver(boolean isOver) {
    this.isGameOver = isOver;
  }

  public void setCurrentPlayer(Player player) {
    this.currentPlayer = player;
  }

  public void setCell(int row, int col, Cell cell) {
    this.grid.setCell(row, col, cell);
  }
}