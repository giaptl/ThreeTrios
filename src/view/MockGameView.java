package view;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import controller.PlayerActionListener;
import model.ICard;
import player.IPlayer;

/**
 * Mock for our GameView class, used for testing the controller class.
 */
public class MockGameView implements IGameView {

  private String lastErrorMessage;
  private IPlayer lastUpdatedPlayer;
  private ICard lastUpdatedCard;
  private int lastUpdatedRow;
  private int lastUpdatedCol;
  private IPlayer lastRemovedCardPlayer;
  private ICard lastRemovedCard;
  private boolean refreshCalled;
  private IPlayer gameOverWinner;
  private int gameOverScore;
  private String gameOverMessage;
  private final List<PlayerActionListener> listeners = new ArrayList<>();

  public MockGameView() {
    // Not needed but kept for consistency/clarity while reading through code.
  }

  @Override
  public void showError(String message) {
    this.lastErrorMessage = message;
  }

  @Override
  public void updateCardSelection(IPlayer player, ICard card) {
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
  public void updateGridCell(int row, int col, ICard card) {
    this.lastUpdatedRow = row;
    this.lastUpdatedCol = col;
    this.lastUpdatedCard = card;
  }

  @Override
  public void removeCardFromHandPanel(IPlayer player, ICard card) {
    this.lastRemovedCardPlayer = player;
    this.lastRemovedCard = card;
  }

  @Override
  public void refreshView() {
    this.refreshCalled = true;
  }

  @Override
  public void setController(Controller controller) {
    // Not needed for mock model.
  }

  @Override
  public void showGameOver(IPlayer winner, int score) {
    this.gameOverWinner = winner;
    this.gameOverScore = score;
    if (gameOverWinner == null) {
      gameOverMessage = "Game over!\nIt's a tie!\nScore for both players: " + gameOverScore;
    } else {
      gameOverMessage = "Game over!\nWinner: " + gameOverWinner.getName() + "\nWinner's Score: "
              + gameOverScore;
    }
  }

  public String getGameOverMessage() {
    return gameOverMessage;
  }

  // Getter methods for verifying calls
  public String getLastErrorMessage() {
    return lastErrorMessage;
  }

  public IPlayer getLastUpdatedPlayer() {
    return lastUpdatedPlayer;
  }

  public ICard getLastUpdatedCard() {
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

  public ICard getLastRemovedCard() {
    return lastRemovedCard;
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

  public void resetViewRefreshed() {
    refreshCalled = false;
  }

  public boolean isViewRefreshed() {
    return refreshCalled;
  }
}
