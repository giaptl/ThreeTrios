package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Represents the initial game model created for ThreeTrios.
 */
public class GameModel implements ThreeTriosModel {

  private Grid grid;
  private Player currentPlayer;
  private Player pRed;
  private Player pBlue;
  private boolean isGameOver;

  /**
   * Creates a new game model.
   */
  public GameModel() {
    this.isGameOver = false;
  }

  @Override
  public Player getRedPlayer() {
    return pRed;
  }

  @Override
  public Player getBluePlayer() {
    return pBlue;
  }

  @Override
  public void startGameWithConfig(Grid grid, List<Card> cards, boolean shuffle) {
    this.grid = grid;
    if (shuffle) {
      Collections.shuffle(cards);
    }

    int numCardCells = grid.getNumCardCells();
    if (numCardCells % 2 == 0) {
      throw new IllegalArgumentException("Number of card cells must be odd.");
    }

    int totalCardsNeeded = numCardCells + 1;
    if (cards.size() < totalCardsNeeded) {
      throw new IllegalArgumentException("Not enough cards to start game.");
    }

    int cardsPerPlayer = (numCardCells + 1) / 2;
    List<Card> redHand = new ArrayList<>(cards.subList(0, cardsPerPlayer));
    List<Card> blueHand = new ArrayList<>(cards.subList(cardsPerPlayer + 1, (cardsPerPlayer * 2)));

    pRed = new Player("Red", redHand);
    pBlue = new Player("Blue", blueHand);

    currentPlayer = pRed;
    isGameOver = false;
  }


  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public Player getOpponent(Player player) {
    if (player.equals(pRed)) {
      return pBlue;
    }
    return pRed;
  }

  @Override
  public Grid getGrid() {
    return grid.copyOfGrid();
  }

  @Override
  public List<Card> getPlayerHand(Player player) {
    return player.getHand();
  }

  @Override
  public boolean isGameOver() {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        Cell cell =  grid.getCell(row, col);
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

    int[] counts = countOccupiedCells(redCardCount, blueCardCount);
    redCardCount = counts[0];
    blueCardCount = counts[1];

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

  // Helper method that counts the number of occupied cells for each player.
  private int[] countOccupiedCells(int redCardCount, int blueCardCount) {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        CardCell cell = (CardCell) grid.getCell(row, col);
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
    return new int[]{redCardCount, blueCardCount};
  }

  @Override
  public void playCard(Player player, Card card, int row, int col) {
    playCardConditions(player, row, col, card);

    CardCell cardCell = new CardCell(card, player);
    grid.setCell(row, col, cardCell);
    player.removeCard(card);
    startBattlePhase(row, col);

    // Switch the current player
    currentPlayer = currentPlayer.equals(pRed) ? pBlue : pRed;

    // Check if the game is over
    isGameOver = isGameOver();

  }

  // Helper method to check the conditions for playing a card.
  private void playCardConditions(Player player, int row, int col, Card cardToPlay) {
    if (!player.equals(currentPlayer)) {
      throw new IllegalArgumentException("It is not " + player.getName() + "'s turn.");
    }

    if (!player.getHand().contains(cardToPlay)) {
      throw new IllegalArgumentException("Card is not in player's hand.");
    }

    Cell cell = grid.getCell(row, col);

    if (!(isValidCell(row, col))) {
      throw new IllegalArgumentException("Invalid row or column.");
    }

    if (cell.isHole()) {
      throw new IllegalArgumentException("Card cannot be placed on a hole.");
    }

    if (!cell.isEmpty()) {
      System.out.println("Cell at (" + row + "," + col + ") isEmpty: " + cell.isEmpty());
      throw new IllegalArgumentException("Cell is not empty.");
    }

    if (isGameOver) {
      throw new IllegalArgumentException("Game is over.");
    }

  }

  // make sure the combo move is working here too
  @Override
  public void startBattlePhase(int row, int col) {
    Cell cell = grid.getCell(row, col);
    if (cell.isHole()) {
      throw new IllegalArgumentException("No card at the specified cell or cell is a hole.");
    }
    CardCell cardCell = (CardCell) cell;
    Player owner = cardCell.getOwner();

    processBattlePhase(owner, row, col);
  }

  @Override
  public int getNumCardsAbleToFlip(Player player, Card card, int row, int col) {
    // Save the current state of the grid
    Grid originalGrid = grid.copyOfGrid();

    // Temporarily place the card on the grid
    CardCell tempCardCell = new CardCell(card, player);
    grid.setCell(row, col, tempCardCell);

    // Simulate the battle phase and count the number of cards flipped
    int cardsFlipped = simulateEntireBattlePhase(player, row, col);

    // Revert the grid to its original state
    grid.setGrid(originalGrid);
    return cardsFlipped;
  }

  // this works for now want to try easier ways and stuff
  private int simulateEntireBattlePhase(Player player, int startRow, int startCol) {
    return processBattlePhase(player, startRow, startCol);
  }

  private int processBattlePhase(Player player, int startRow, int startCol) {
    int cardsFlipped = 0;
    Queue<int[]> toProcess = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    toProcess.offer(new int[]{startRow, startCol});
    visited.add(startRow + "," + startCol);

    while (!toProcess.isEmpty()) {
      int[] current = toProcess.poll();
      int row = current[0], col = current[1];
      CardCell currentCell = (CardCell) grid.getCell(row, col);

      if (currentCell == null || currentCell.getCard() == null) {
        continue;
      }

      cardsFlipped += processAdjacentCells(player, currentCell.getCard(), row, col, visited, toProcess);
    }
    return cardsFlipped;
  }

  private int processAdjacentCells(Player player, Card currentCard, int row, int col,
                                   Set<String> visited, Queue<int[]> toProcess) {
    int flipped = 0;
    for (Direction direction : Direction.values()) {
      int newRow = row + direction.getRowOffset();
      int newCol = col + direction.getColOffset();
      String cellKey = newRow + "," + newCol;

      if (isValidCell(newRow, newCol) && !visited.contains(cellKey)) {
        visited.add(cellKey);
        int flippedInDirection = cardAttackDirections(direction, newRow, newCol, player, currentCard);
        flipped += flippedInDirection;
        if (flippedInDirection > 0) {
          toProcess.offer(new int[]{newRow, newCol});
        }
      }
    }
    return flipped;
  }


  private boolean isValidCell(int row, int col) {
    return row >= 0 && row < grid.getRows() && col >= 0
            && col < grid.getColumns();
  }


  private int cardAttackDirections(Direction direction, int newRow, int newCol, Player owner, Card card) {
    int cardsFlipped = 0;
    if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getColumns()) {
      Cell adjacentCell = grid.getCell(newRow, newCol);

      if (!(adjacentCell.isHole())) {
        CardCell adjacentCardCell = (CardCell) adjacentCell;
        Card adjacentCard = adjacentCardCell.getCard();
        Player adjacentOwner = adjacentCardCell.getOwner();

        if (adjacentCard != null && !owner.equals(adjacentOwner)) {
          int attackValue = parseAttackValue(card.getAttackValue(direction));
          int defenseValue = parseAttackValue(adjacentCard.getAttackValue(direction.getOpposite()));

          if (attackValue > defenseValue) {
            adjacentCardCell.setOwner(owner);
            cardsFlipped++;
          }
        }
      }
    }
    return cardsFlipped;
  }


  private int parseAttackValue(String value) {
    return "A".equals(value) ? 10 : Integer.parseInt(value);
  }


  @Override
  public int getPlayerScore(Player player) {
    int redCardCount = pRed.getHand().size();
    int blueCardCount = pBlue.getHand().size();

    int[] counts = countOccupiedCells(redCardCount, blueCardCount);
    redCardCount = counts[0];
    blueCardCount = counts[1];

    if (player == pRed) {
      return redCardCount;
    } else if (player == pBlue) {
      return blueCardCount;
    }
    return -1;
  }
}