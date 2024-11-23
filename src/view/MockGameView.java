package view;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerActionListener;
import model.Card;
import player.IPlayer;

public class MockGameView implements IGameView {

  private String lastErrorMessage;
  private IPlayer lastUpdatedPlayer;
  private Card lastUpdatedCard;
  private int lastUpdatedRow;
  private int lastUpdatedCol;
  private IPlayer lastRemovedCardPlayer;
  private Card lastRemovedCard;
  private boolean refreshCalled;
  private IPlayer gameOverWinner;
  private int gameOverScore;
  private final List<PlayerActionListener> listeners = new ArrayList<>();

  public MockGameView() {

  }

  @Override
  public void showError(String message) {
    this.lastErrorMessage = message;
  }

  @Override
  public void updateCardSelection(IPlayer player, Card card) {
    this.lastUpdatedPlayer = player;
    this.lastUpdatedCard = card;
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removePlayerActionListener(PlayerActionListener listener) {
    listeners.remove(listener);
  }

  @Override
  public void updateGridCell(int row, int col, Card card) {
    this.lastUpdatedRow = row;
    this.lastUpdatedCol = col;
    this.lastUpdatedCard = card;
  }

  @Override
  public void removeCardFromHandPanel(IPlayer player, Card card) {
    this.lastRemovedCardPlayer = player;
    this.lastRemovedCard = card;
  }

  @Override
  public void refreshView() {
    this.refreshCalled = true;
  }

  @Override
  public void showGameOver(IPlayer winner, int score) {
    this.gameOverWinner = winner;
    this.gameOverScore = score;
  }

  // Getter methods for verifying calls
  public String getLastErrorMessage() {
    return lastErrorMessage;
  }

  public IPlayer getLastUpdatedPlayer() {
    return lastUpdatedPlayer;
  }

  public Card getLastUpdatedCard() {
    return lastUpdatedCard;
  }

  public int getLastUpdatedRow() {
    return lastUpdatedRow;
  }

  public int getLastUpdatedCol() {
    return lastUpdatedCol;
  }

  public IPlayer getLastRemovedCardPlayer() {
    return lastRemovedCardPlayer;
  }

  public Card getLastRemovedCard() {
    return lastRemovedCard;
  }

  public boolean isRefreshCalled() {
    return refreshCalled;
  }

  public IPlayer getGameOverWinner() {
    return gameOverWinner;
  }

  public int getGameOverScore() {
    return gameOverScore;
  }

  public List<PlayerActionListener> getListeners() {
    return new ArrayList<>(listeners);
  }

  // Reset method for clearing tracked data between tests
  public void reset() {
    lastErrorMessage = null;
    lastUpdatedPlayer = null;
    lastUpdatedCard = null;
    lastUpdatedRow = -1;
    lastUpdatedCol = -1;
    lastRemovedCardPlayer = null;
    lastRemovedCard = null;
    refreshCalled = false;
    gameOverWinner = null;
    gameOverScore = -1;
    listeners.clear();
  }

}
