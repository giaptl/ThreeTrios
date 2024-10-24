package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameModel implements IGameModel {

  private Grid grid;
  private Player currentPlayer;
  private Map<Player, List<Card>> playerHands;
  private Player pRed;
  private Player pBlue;
  boolean isGameOver;

  public GameModel() {
    this.playerHands = new HashMap<>();
  }

  @Override
  public void startGame(Grid grid, List<Card> cards, boolean shuffle) {
    this.grid = grid;

    int numCardCells = grid.getNumCardCells();
    int totalCardsNeeded = numCardCells + 1;

    if (cards.size() < totalCardsNeeded) {
      throw new IllegalArgumentException("Not enough cards to start game.");
    }

    if (shuffle) {
      Collections.shuffle(cards);
    }

    int cardsPerPlayer = (numCardCells + 1) / 2;

    List<Card> redHand = new ArrayList<>(cards.subList(0, cardsPerPlayer));
    List<Card> blueHand = new ArrayList<>(cards.subList(cardsPerPlayer + 1, (cardsPerPlayer * 2)));

    pRed = new Player("Red", redHand);
    pBlue = new Player("Blue", blueHand);

    playerHands.put(pRed, redHand);
    playerHands.put(pBlue, blueHand);
    currentPlayer = pRed;


    isGameOver = false;
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public Grid getGrid() {
    return grid;
  }

  @Override
  public List<Card> getPlayerHand(Player player) {
    return playerHands.get(player);
  }

  @Override
  public boolean isGameOver() {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        Cell cell = grid.getCell(row, col);
        if (!cell.isHole() && cell.isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public Player getWinner() {
    int redCardCount = pRed.getHand().size();
    int blueCardCount = pBlue.getHand().size();

    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        Cell cell = grid.getCell(row, col);
        if (cell.isOccupied()) {
          Player owner = cell.getOwner();
          if (owner.equals(pRed)) {
            redCardCount++;
          } else if (owner.equals(pBlue)) {
            blueCardCount++;
          }
        }
      }
    }

    if (redCardCount > blueCardCount) {
      // Red Player wins
      return pRed;
    } else if (blueCardCount > redCardCount) {
      // Blue player wins
      return pBlue;
    } else {
      // Tie
      return null;
    }
  }

  @Override
  public void playCard(Player player, Card card, int row, int col) {

  }

  @Override
  public void startBattlePhase(int row, int col) {

  }
}
