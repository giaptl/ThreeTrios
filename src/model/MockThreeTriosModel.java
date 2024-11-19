package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static model.CardValues.A;

/**
 * Represents the initial game model created for ThreeTrios.
 * This class manages the state and logic of the game, including the grid, players, and game rules.
 */
public class MockThreeTriosModel implements ThreeTriosModel {
  private Grid grid;
  private Player currentPlayer;
  private Player redPlayer;
  private Player bluePlayer;
  private boolean isGameOver;

  /**
   * Constructs a new MockGameModel with a 3x3 grid and two players with cards.
   */
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
    isGameOver = false;
  }

  /**
   * Not used really but needed for implementation in testing.
   */
  public void startGameWithConfig(Grid grid) {
    this.grid = grid;
    isGameOver = false;
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
    startBattlePhase(row, col);
    currentPlayer = currentPlayer.equals(redPlayer) ? bluePlayer : redPlayer;
    isGameOver = isGameOver();
  }

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
  public void addModelStatusListener(ModelStatusListener listener) {
    // Not used.
  }

  @Override
  public void removeModelStatusListener(ModelStatusListener listener) {
    // not used.
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

  /**
   * Simulation of entire battle phase. Abstracted into its own method for code clarity.
   */
  private int simulateEntireBattlePhase(Player player, int startRow, int startCol) {
    return processBattlePhase(player, startRow, startCol);
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

    int redCardCount = redPlayer.getHand().size();
    int blueCardCount = bluePlayer.getHand().size();

    int[] counts = countOccupiedCells(redCardCount, blueCardCount);
    redCardCount = counts[0];
    blueCardCount = counts[1];

    if (redCardCount > blueCardCount) {
      // Red Player wins
      return redPlayer;
    } else if (blueCardCount > redCardCount) {
      // Blue player wins
      return bluePlayer;
    } else {
      // Tie
      return null;
    }
  }

  /**
   * Helper method that counts the number of occupied cells for each player.
   */
  private int[] countOccupiedCells(int redCardCount, int blueCardCount) {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        CardCell cell = (CardCell) grid.getCell(row, col);
        if (cell.isOccupied()) {
          Player owner = cell.getOwner();
          if (owner.equals(redPlayer)) {
            redCardCount++;
          } else if (owner.equals(bluePlayer)) {
            blueCardCount++;
          }
        }
      }
    }
    return new int[]{redCardCount, blueCardCount};
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

  /**
   * Additional methods for testing. Used to setGameOver.
   */
  public void setGameOver(boolean isOver) {
    this.isGameOver = isOver;
  }

  /**
   * Used to set the current player to whoever is passed in.
   */
  public void setCurrentPlayer(Player player) {
    this.currentPlayer = player;
  }

  /**
   * Helper method which abstracts out much of the battle phase
   * code from both the simulation and the actual battle phase.
   */
  protected int processBattlePhase(Player player, int startRow, int startCol) {
    int cardsFlipped = 0;
    Queue<int[]> toProcess = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    toProcess.offer(new int[]{startRow, startCol});

    while (!toProcess.isEmpty()) {
      int[] current = toProcess.poll();
      int row = current[0];
      int col = current[1];
      String cellKey = row + "," + col;

      if (visited.contains(cellKey)) {
        continue;
      }
      visited.add(cellKey);

      CardCell currentCell = (CardCell) grid.getCell(row, col);
      if (currentCell == null || currentCell.getCard() == null) {
        continue;
      }

      cardsFlipped += processAdjacentCells(
              player, currentCell.getCard(), row, col, visited, toProcess);
    }
    return cardsFlipped;
  }

  /**
   * Checks to see which cells are being flipped and also flipping cards
   * that have already been flipped.
   */
  protected int processAdjacentCells(Player player, Card currentCard, int row, int col,
                                     Set<String> visited, Queue<int[]> toProcess) {
    int flipped = 0;
    for (Direction direction : Direction.values()) {
      int newRow = row + direction.getRowOffset();
      int newCol = col + direction.getColOffset();
      String cellKey = newRow + "," + newCol;

      if (isValidCell(newRow, newCol)) {
        int flippedInDirection = cardAttackDirections(
                direction, newRow, newCol, player, currentCard);
        flipped += flippedInDirection;
        if (flippedInDirection > 0) {
          toProcess.offer(new int[]{newRow, newCol});
        }
      }
    }
    return flipped;
  }

  /**
   * Helper method that abstracted out redundant code to check if a CELL is valid.
   */
  protected boolean isValidCell(int row, int col) {
    return row >= 0 && row < grid.getRows() && col >= 0 && col < grid.getColumns();
  }

  /**
   * This method allows us to flip cards in the 4 directions and keep track of how many cards
   * are flipped from each turn.
   */
  protected int cardAttackDirections(Direction direction, int newRow, int newCol,
                                     Player owner, Card card) {
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

  /**
   * Finds the attack value. Method helps us deal with the A representing the value 10 as an int.
   */
  protected int parseAttackValue(String value) {
    return "A".equals(value) ? 10 : Integer.parseInt(value);
  }

}